package cn.tedu.cart.service.impl;

import cn.tedu.ProductApi;
import cn.tedu.cart.mapper.CartMapper;
import cn.tedu.cart.service.CartService;
import common.pojo.Cart;
import common.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired(required = false)
    private CartMapper cartMapper;
//    @Autowired
//    private RestTemplate restTemplate;
    @Autowired
    private ProductApi productApi;

    @Override
    public List<Cart> queryCart(String userId) {
        return cartMapper.queryCart(userId);
    }

    @Override
    public void addCart(Cart cart) {
        /*
            cart 非空属性 userId productId num
            1 应该使用userId productId 查询已存在
            2 存在 旧num+新num更新数据库
            3 不存在 调用商品微服务获取商品id对应的product对象
            封装cart写入数据库
        */
        Cart exist = cartMapper.selectExistCartByUserIdAndProductId(cart);
        if (exist == null) {
//            String url = "http://product-service/product/manage/item/" + cart.getProductId();
//            Product product = restTemplate.getForObject(url, Product.class);
            Product product = productApi.getProductById(cart.getProductId());
            cart.setProductName(product.getProductName());
            cart.setProductPrice(product.getProductPrice());
            cart.setProductImage(product.getProductImgurl());
            cartMapper.insertCart(cart);
        } else {
            cart.setNum(cart.getNum() + exist.getNum());
            cartMapper.updateNumByUserIdAndProductId(cart);
        }


    }

    @Override
    public void updateCart(Cart cart) {
        cartMapper.updateNumByUserIdAndProductId(cart);
    }

    @Override
    public void deleteCart(Cart cart) {
        cartMapper.deleteCartByUserIdAndProductId(cart);
    }
}
