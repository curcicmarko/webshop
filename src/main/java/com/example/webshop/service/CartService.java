package com.example.webshop.service;

import com.example.webshop.model.dto.CartDto;
import com.example.webshop.model.entity.Cart;
import com.example.webshop.model.entity.CartItem;
import com.example.webshop.model.entity.Product;
import com.example.webshop.model.entity.User;
import com.example.webshop.model.mapper.CartMapper;
import com.example.webshop.repository.CartItemRepository;
import com.example.webshop.repository.CartRepository;
import com.example.webshop.repository.ProductRepository;
import com.example.webshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;


    public List<CartDto> getCarts() {
        return cartRepository.findAll().stream()
                .map(CartMapper::toDto)
                .collect(Collectors.toList());

    }

    public CartDto getCart(Long cartId){
        System.out.println("Usao u cart service");
        System.out.println(cartId);
        Cart cart = cartRepository.findById(cartId).orElseThrow(()-> new IllegalArgumentException("No Cart with id: +"+cartId));
        System.out.println(cartId);
        return CartMapper.toDto(cart);
    }
    public CartDto addToCart(Long cartId, Long productId, int quantity, Long userId) {

        Cart cart = cartRepository.findById(cartId).orElseGet(Cart::new);
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("No User with id: " + userId));
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("No Producit with id: " + productId));

        if (cart.getUser() == null) {
            cart.setTotalPrice(0);
            cart.setUser(user);
            cartRepository.save(cart);
        }

        if (product.getAvailableQuantity() < quantity)
            throw new IllegalArgumentException("Requested quantity exceeds available quantity");

        CartItem existingCartItem = cart.getCartItems().stream()
                .filter(i -> i.getProduct().getId() == productId)
                .findFirst().orElse(null);

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            cart.setTotalPrice(existingCartItem.getQuantity() * existingCartItem.getPrice());
            cartItemRepository.save(existingCartItem);
            System.out.println("Usao u drugi");


        } else {

            CartItem cartItem = new CartItem();
            cartItem.setQuantity(quantity);
            cartItem.setPrice(product.getPrice());
            cartItem.setProduct(product);
            cart.getCartItems().add(cartItem);
            cartItem.setCart(cart);
            cartItemRepository.save(cartItem);


        }
        cart.setTotalPrice(cart.getCartItems().stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .reduce(0, Double::sum));

        user.getCarts().add(cart);
        userRepository.save(user);
        cartRepository.save(cart);

        return CartMapper.toDto(cart);


    }


}
