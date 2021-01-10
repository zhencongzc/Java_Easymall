package cn.tedu.cart.service;

import common.pojo.Cart;

import java.util.List;

public interface CartService {
    List<Cart> queryCart(String userId);

    void addCart(Cart cart);

    void updateCart(Cart cart);

    void deleteCart(Cart cart);
}
