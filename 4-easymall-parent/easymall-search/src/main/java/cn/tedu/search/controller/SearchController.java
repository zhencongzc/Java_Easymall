package cn.tedu.search.controller;

import cn.tedu.search.service.SearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import common.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {
    @Autowired
    private SearchService searchService;

    @RequestMapping("/search/manage/query")
    public List<Product> search(String query, Integer page, Integer rows) throws JsonProcessingException {
        if (page == null) {
            page = 1;
        }
        if (rows == null) {
            rows = 5;
        }
        return searchService.search(query, page, rows);
    }
}
