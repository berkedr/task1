package com.example.demo.controller;


import com.example.demo.entity.Cart;
import com.example.demo.service.CartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{customerId}")
    public Cart getCart(@PathVariable Long customerId) {
        return cartService.getCart(customerId);
    }

    @PostMapping("/{cartId}/add-product/{productId}")
    public Cart addProductToCart(@PathVariable Long cartId, @PathVariable Long productId, @RequestParam int quantity) {
        return cartService.addProductToCart(cartId, productId, quantity);
    }

    @DeleteMapping("/{cartId}/remove-product/{productId}")
    public Cart removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        return cartService.removeProductFromCart(cartId, productId);
    }

    @DeleteMapping("/{cartId}/empty")
    public Cart emptyCart(@PathVariable Long cartId) {
        return cartService.emptyCart(cartId);
    }
}