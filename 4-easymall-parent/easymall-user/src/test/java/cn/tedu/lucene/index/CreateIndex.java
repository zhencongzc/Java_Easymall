package cn.tedu.lucene.index;

import cn.tedu.lucene.analyzer.IKAnalyzer6x;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateIndex {
    @Test
    public void createIndex() throws IOException {
        Path path = Paths.get("d://index");
        FSDirectory dir = FSDirectory.open(path);

        IndexWriterConfig config = new IndexWriterConfig(new IKAnalyzer6x());
        config.setOpenMode(IndexWriterConfig.OpenMode.APPEND);
         /*
            CREATE:d:/index01 没有则创建,有则覆盖
            APPEND:索引文件没有则报错,有则追加
            CREATE_OR_APPEND:没有则创建,有则追加
         */
        IndexWriter writer = new IndexWriter(dir, config);
        Document doc1 = new Document();
        Document doc2 = new Document();
        //属性,list封装 IndexableField域属性 文本和数字类型
        doc1.add(new TextField("title", "美国特朗普不要脸", Field.Store.YES));
        /*
            域属性发问:
            1 域属性有多少种 IntPoint StringField/TextField
            2 TextField中 Store.YES/NO是什么意思
         */
        doc1.add(new TextField("content", "频繁甩锅,控诉中国疫情信息透露不明确", Field.Store.YES));
        doc1.add(new TextField("publisher", "新华网", Field.Store.YES));
        doc1.add(new TextField("address", "http://www.xinwen.com/news", Field.Store.YES));
        doc1.add(new IntPoint("click", 158));
        doc1.add(new StringField("click", "158次", Field.Store.YES));
        doc2.add(new TextField("title", "美国蓬佩奥牛逼", Field.Store.YES));
        doc2.add(new TextField("content", "美国谎称,援助中国1亿美金,中国外交部称从未收到", Field.Store.YES));
        doc2.add(new TextField("publisher", "新华网", Field.Store.YES));
        doc2.add(new StringField("address", "https://news.zhengzhi.org/", Field.Store.YES));
        doc2.add(new IntPoint("click", 58));
        doc2.add(new StringField("click", "58次", Field.Store.YES));
        //创建出索引文件
        writer.addDocument(doc1);
        writer.addDocument(doc2);
        writer.commit();
        System.out.println("成功创建索引");
    }
}
