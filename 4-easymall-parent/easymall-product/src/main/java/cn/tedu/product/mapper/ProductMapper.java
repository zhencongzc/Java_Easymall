package cn.tedu.product.mapper;

import common.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int selectCount();

    List<Product> selectProductByPage(@Param("start") int start, @Param("rows") Integer rows);

    Product selectProductById(String productId);

    void insertProduct(Product product);

    void updateProduct(Product product);
}
