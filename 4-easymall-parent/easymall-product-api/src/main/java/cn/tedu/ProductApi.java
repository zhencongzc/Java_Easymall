package cn.tedu;

import common.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("product-service")
public interface ProductApi {
    @RequestMapping(value = "/product/manage/item/{productId}", method = RequestMethod.GET)
    public Product getProductById(@PathVariable("productId") String productId);
}
