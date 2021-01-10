package cn.tedu.product.controller;

import cn.tedu.product.service.ProductService;
import common.pojo.Product;
import common.vo.EasyUIResult;
import common.vo.SysResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/manage")
public class ProductController {
    @Autowired
    private ProductService productService;

    //商品分页查询
    @RequestMapping("/pageManage")
    public EasyUIResult queryByPage(Integer page, Integer rows) {
        if (page == null || rows == null) {
            page = 1;
            rows = 5;
        }
        return productService.queryByPage(page, rows);
    }

    //查询单个商品
    @RequestMapping("/item/{productId}")
    public Product queryOneProduct(@PathVariable("productId") String productId) {
        return productService.queryOneProduct(productId);
    }

    //新增商品
    @RequestMapping("/save")
    public SysResult addProduct(Product product) {
        try {
            productService.addProduct(product);
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, "新增失败", null);
        }
    }

    //修改商品
    @RequestMapping("/update")
    public SysResult updateProduct(Product product) {
        try {
            productService.updateProduct(product);
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, "修改失败", null);
        }
    }
}
