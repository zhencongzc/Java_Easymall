package cn.tedu.cart.mapper;

import common.pojo.Cart;

import java.util.List;

public interface CartMapper {
    List<Cart> queryCart(String userId);

    Cart selectExistCartByUserIdAndProductId(Cart cart);

    void insertCart(Cart cart);

    void updateNumByUserIdAndProductId(Cart cart);

    void deleteCartByUserIdAndProductId(Cart cart);
}
