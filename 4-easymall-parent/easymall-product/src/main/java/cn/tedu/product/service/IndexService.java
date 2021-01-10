package cn.tedu.product.service;

import cn.tedu.product.mapper.ProductMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.pojo.Product;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexService {
    @Autowired
    private TransportClient client;
    @Autowired(required = false)
    private ProductMapper productMapper;
    private static final ObjectMapper mapper = new ObjectMapper();

    public void indexCreate() throws JsonProcessingException {
        //创建索引
        String indexName = "easymall";
        String typeName = "product";
        IndicesAdminClient indices = client.admin().indices();
        boolean exists = indices.prepareExists(indexName).get().isExists();
        if (!exists) {
            indices.prepareCreate(indexName).get();
        }

        //创建文档
        List<Product> products = productMapper.selectProductByPage(0, 100);
        for (Product product : products) {
            String json = mapper.writeValueAsString(product);
            IndexRequestBuilder request = client.prepareIndex(indexName, typeName, product.getProductId());
            request.setSource(json).get();
        }
    }
}
