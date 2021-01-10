package cn.tedu.lucene.index;


import cn.tedu.lucene.analyzer.IKAnalyzer6x;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;

/**
 * 实现测试不同的查询功能
 */
public class SearchIndex {
    @Test
    public void termQuery() throws IOException {
        Path path = Paths.get("d:/index");
        FSDirectory dir = FSDirectory.open(path);
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        Term term = new Term("title", "美国");
        Query query = new TermQuery(term);
        int page = 2;
        int rows = 4;
        TopDocs topDocs = searcher.search(query, page * rows);
        System.out.println("总命中:" + topDocs.totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        int count = 0;
        for (ScoreDoc scoreDoc : scoreDocs) {
            //在scoreDoc中,包含documentId
            if (count < ((page - 1) * rows)) {
                count++;
                //count小于起始位置,不读数据
                continue;
            }
            count++;
            int docId = scoreDoc.doc;
            Document doc = searcher.doc(docId);
            System.out.println("title:" + doc.get("title"));
            System.out.println("content:" + doc.get("content"));
            System.out.println("publisher:" + doc.get("publisher"));
            System.out.println("address:" + doc.get("address"));
            System.out.println("click:" + doc.get("click"));
        }
    }

    //多域查询 title,content
    @Test
    public void multiFieldQuery() throws Exception {
        Path path = Paths.get("d:/index");
        FSDirectory dir = FSDirectory.open(path);
        IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(dir));
        //Query条件封装
        //准备多个域
        String[] fields = {"title", "content"};
        //多域查询解析器
        MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, new IKAnalyzer6x());
        Query query = parser.parse("中国美国外交");
        //parser先利用分词器对text文本做分词解析 中国 美国 国外 外交
        //根据多域创建多个Term对象 title:中国 title:美国 title:国外 title:外交
        //content:中国 content:美国 content:国外 content:外交,只要任意一个词项对应的查询结果
        //都是多域查询的结果集
        TopDocs top = searcher.search(query, 10);
        ScoreDoc[] scoreDocs = top.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId = scoreDoc.doc;
            Document doc = searcher.doc(docId);
            System.out.println("title:" + doc.get("title"));
            System.out.println("content:" + doc.get("content"));
            System.out.println("publisher:" + doc.get("publisher"));
            System.out.println("address:" + doc.get("address"));
            System.out.println("click:" + doc.get("click"));
        }
    }

    @Test
    public void booleanQuery() throws IOException, ParseException {
        Path path = Paths.get("d:/index");
        FSDirectory dir=FSDirectory.open(path);
        IndexSearcher searcher=new IndexSearcher(DirectoryReader.open(dir));
        //Query条件封装
        //布尔查询条件封装 准备子条件
        Query query1=new TermQuery(new Term("title","美国"));
        Query query2=new TermQuery(new Term("content","美国"));
        BooleanClause bc1=new BooleanClause(query1, BooleanClause.Occur.MUST);
        BooleanClause bc2=new BooleanClause(query2, BooleanClause.Occur.MUST_NOT);
        //MUST:表示布尔结果集必须是该子条件的子集
        //MUST_NOT:表示布尔结果集必须不是该子条件的子集
        Query query=new BooleanQuery.Builder().add(bc1).add(bc2).build();
        TopDocs top = searcher.search(query, 10);
        ScoreDoc[] scoreDocs = top.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId=scoreDoc.doc;
            Document doc = searcher.doc(docId);
            System.out.println("title:"+doc.get("title"));
            System.out.println("content:"+doc.get("content"));
            System.out.println("publisher:"+doc.get("publisher"));
            System.out.println("address:"+doc.get("address"));
            System.out.println("click:"+doc.get("click"));
        }
    }

    //范围查询
    @Test
    public void rangeQuery() throws Exception {
        Path path = Paths.get("d:/index");
        FSDirectory dir=FSDirectory.open(path);
        IndexSearcher searcher=new IndexSearcher(DirectoryReader.open(dir));
        //Query条件封装
        //搜索click在50-60之间的范围有哪些数据
        Query query = IntPoint.newRangeQuery("click", 50, 60);
        TopDocs top = searcher.search(query, 10);
        ScoreDoc[] scoreDocs = top.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId=scoreDoc.doc;
            Document doc = searcher.doc(docId);
            System.out.println("title:"+doc.get("title"));
            System.out.println("content:"+doc.get("content"));
            System.out.println("publisher:"+doc.get("publisher"));
            System.out.println("address:"+doc.get("address"));
            System.out.println("click:"+doc.get("click"));
        }
    }

}
