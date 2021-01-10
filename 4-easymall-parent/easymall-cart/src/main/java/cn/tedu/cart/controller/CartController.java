package cn.tedu.cart.controller;

import cn.tedu.cart.service.CartService;
import common.pojo.Cart;
import common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart/manage")
public class CartController {
    @Autowired
    private CartService cartService;

    @RequestMapping("/query")
    public List<Cart> queryCart(String userId) {
        return cartService.queryCart(userId);
    }

    @RequestMapping("/save")
    public SysResult addCart(Cart cart) {
        try {
            cartService.addCart(cart);
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, "新增失败", null);
        }
    }

    @RequestMapping("/update")
    public SysResult updateCart(Cart cart) {
        try {
            cartService.updateCart(cart);
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, "更新失败", null);
        }
    }

    @RequestMapping("/delete")
    public SysResult deleteCart(Cart cart) {
        try {
            cartService.deleteCart(cart);
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, "删除失败", null);
        }
    }
}
