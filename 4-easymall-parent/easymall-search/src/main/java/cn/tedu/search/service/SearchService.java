package cn.tedu.search.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.pojo.Product;
import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {
    @Autowired
    private TransportClient client;
    private static final ObjectMapper mapper = new ObjectMapper();

    public List<Product> search(String text, Integer page, Integer rows) throws JsonProcessingException {
        MatchQueryBuilder query = QueryBuilders.matchQuery("productName", text);
        SearchRequestBuilder request = client.prepareSearch("easymall");
        SearchResponse response = request.setQuery(query).setFrom((page - 1) * rows).setSize(rows).get();
        SearchHits topHits = response.getHits();
        SearchHit[] hits = topHits.getHits();
        List<Product> pList = new ArrayList<>();
        for (SearchHit hit : hits) {
            String json = hit.getSourceAsString();
            Product product = mapper.readValue(json, Product.class);
            pList.add(product);
        }
        return pList;
    }
}
