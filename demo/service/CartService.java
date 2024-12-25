package com.example.demo.service;


import com.example.demo.entity.Cart;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Product;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public Cart getCart(Long customerId) {
        return cartRepository.findByCustomer_Id(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }

    public Cart addProductToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (product.getStock() < quantity) {
            throw new IllegalStateException("Not enough stock available for product: " + product.getName());
        }

        OrderItem item = new OrderItem();
        item.setCart(cart);
        item.setProduct(product);
        item.setPriceAtPurchase(product.getPrice());
        item.setQuantity(quantity);
        cart.getItems().add(item);

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

        updateCartTotalPrice(cart);
        return cartRepository.save(cart);
    }

    public Cart removeProductFromCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        updateCartTotalPrice(cart);
        return cartRepository.save(cart);
    }

    public Cart emptyCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        cart.getItems().clear();
        cart.setTotalPrice(0.0);
        return cartRepository.save(cart);
    }

    private void updateCartTotalPrice(Cart cart) {
        double totalPrice = cart.getItems().stream()
                .mapToDouble(item -> item.getPriceAtPurchase() * item.getQuantity())
                .sum();
        cart.setTotalPrice(totalPrice);
    }
}