package cn.tedu.es;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import javax.management.Query;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class TransportTest {
    //索引管理
    private TransportClient client;

    @Before
    public void initTrans() throws UnknownHostException {
        //初始化方法 client赋值
        //SETTINGS指定当前连接对象,连接的集群名称
        //EMPTY是默认名称cluster.name=elasticsearch
        client = new PreBuiltTransportClient(Settings.EMPTY);
        //提供节点信息,3个,至少给一个就可以连接集群
        TransportAddress address = new InetSocketTransportAddress(
                InetAddress.getByName("10.42.168.46"), 9300);
        client.addTransportAddress(address);
    }

    @Test
    public void indexManagement() {
        String indexName = "index02";
        //管理操作索引,获取管理对象
        IndicesAdminClient indices = client.admin().indices();
        //这里使用对象操作es集群,通过prepare预先处理的方式,更接近http使用习惯
        //一次请求一次响应
        //判断索引是否存在
        IndicesExistsRequestBuilder request1 =
                indices.prepareExists(indexName);
        IndicesExistsResponse response1 = request1.get();//发送请求
        boolean exist = response1.isExists();//是否存在 true false
        System.out.println("当前索引" + (exist == true ? "存在" : "不存在"));
        if (!exist) {
            indices.prepareCreate(indexName).get();
            System.out.println("创建index02");
        }
        GetSettingsResponse response = indices.prepareGetSettings(indexName).get();
        System.out.println("获取到索引数据");
        ImmutableOpenMap<String, Settings> indexToSettings = response.getIndexToSettings();
        Settings settings = indexToSettings.get(indexName);
        System.out.println("分片数量:" + settings.get("index.number_of_shards"));
        indices.prepareDelete(indexName).get();
    }

    //文档管理
    @Test
    public void documentManage() {
        //新增文档
        String docJson = "{\"name\":\"王翠花\",\"age\":28,\"信息\":\"个性善良,为人朴实\"}";
        IndexRequestBuilder request1 = client.prepareIndex("index01", "person", "1");
        request1.setSource(docJson);
        IndexResponse response1 = request1.get();
        System.out.println("文档创建成功");
        //获取文档
        GetResponse response2 = client.prepareGet("index01", "person", "1").get();
        String DOCjSON = response2.getSourceAsString();
        System.out.println(DOCjSON);
        //文档删除
        client.prepareDelete("index", "type", "1").get();
    }

    //搜索功能
    @Test
    public void search() {
        //创建检索对象query
        TermQueryBuilder query = QueryBuilders.termQuery("name", "王");
        //构造请求对象查询idex01的所有类型
        SearchRequestBuilder request = client.prepareSearch("index01");
        //提供查询参数包括条件query和分页数据
        request.setQuery(query);
        request.setFrom(0);
        request.setSize(5);
        SearchResponse response = request.get();
        SearchHits topHits = response.getHits();
        System.out.println("总共命中" + topHits.getTotalHits());
        SearchHit[] hits = topHits.getHits();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }

    }

}
