package cn.tedu.product.service;

import common.pojo.Product;
import common.vo.EasyUIResult;

public interface ProductService {
    EasyUIResult queryByPage(Integer page, Integer rows);

    Product queryOneProduct(String productId);

    void addProduct(Product product);

    void updateProduct(Product product);
}
