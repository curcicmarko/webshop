package com.example.webshop.service;

import com.example.webshop.model.dto.CartDto;
import com.example.webshop.model.dto.UserDto;
import com.example.webshop.model.entity.Cart;
import com.example.webshop.model.entity.CartItem;
import com.example.webshop.model.entity.Product;
import com.example.webshop.model.entity.User;
import com.example.webshop.model.mapper.CartMapper;
import com.example.webshop.model.mapper.UserMapper;
import com.example.webshop.repository.CartItemRepository;
import com.example.webshop.repository.CartRepository;
import com.example.webshop.repository.ProductRepository;
import com.example.webshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

    public List<CartDto> getCartsPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Cart> carts = cartRepository.findAll(pageable);
        return carts.stream()
                .map(CartMapper::toDto)
                .collect(Collectors.toList());
    }



    public CartDto getCart(Long cartId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart with Id: " + cartId + " dont exist"));
        return CartMapper.toDto(cart);
    }

    public CartDto addToCart(Long cartId, Long productId, int quantity, Long userId) {

        Cart cart = cartRepository.findById(cartId).orElseGet(Cart::new);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " does not exist"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product with id: " + productId + " does not exist"));

        if (cart.getUser() == null) {
            cart.setTotalPrice(0);
            cart.setUser(user);
            cartRepository.save(cart);
        }

        if (product.getAvailableQuantity() < quantity)
            throw new IllegalArgumentException("Requested quantity exceeds available quantity");

        CartItem existingCartItem = cart.getCartItems().stream()
                .filter(i -> Objects.equals(i.getProduct().getId(), productId))
                .findFirst().orElse(null);

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            cart.setTotalPrice(existingCartItem.getQuantity() * existingCartItem.getPrice());
            cartItemRepository.save(existingCartItem);


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

    public CartDto updateCart(Long cartId, Long productId, int quantity) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart with Id: " + cartId + " does not exist"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product with id: " + productId + " does not exist"));


        if (quantity < 0)
            throw new IllegalArgumentException("Quantity can't be negative number");


        CartItem existingCartItem = cart.getCartItems().stream()
                .filter(i -> Objects.equals(i.getProduct().getId(), productId))
                .findFirst().orElse(null);

        if (existingCartItem == null)
            throw new IllegalArgumentException("Requested Product is not in shopping cart");

        if (quantity == 0) {
            cart.getCartItems().remove(existingCartItem);
            cartItemRepository.delete(existingCartItem);
            cart.setTotalPrice(cart.getCartItems().stream()
                    .mapToDouble(i -> i.getPrice() * i.getQuantity())
                    .reduce(0, Double::sum));

            cartRepository.save(cart);

        } else {

            existingCartItem.setQuantity(quantity);
            cart.getCartItems().stream()
                    .filter(i -> Objects.equals(i.getProduct().getId(), productId))
                    .forEach(i -> i.setQuantity(quantity));

            cartItemRepository.save(existingCartItem);

            cart.setTotalPrice(cart.getCartItems().stream()
                    .mapToDouble(i -> i.getPrice() * i.getQuantity())
                    .reduce(0, Double::sum));

            cartRepository.save(cart);
        }

        return CartMapper.toDto(cart);

    }

    public List<CartDto> findCartByUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with Id: " + userId + " does not exist"));

        return cartRepository.findByUser(user).stream()
                .map(CartMapper::toDto)
                .collect(Collectors.toList());

    }

    public void deleteCart(Long cartId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart with id: " + cartId + " does not exist"));

        cartRepository.delete(cart);
    }


    public void removeItemFromCart(Long cartId, Long productId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart with Id: " + cartId + " does not exist"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product with id: " + productId + " does not exist"));

        CartItem existingCartItem = cart.getCartItems().stream()
                .filter(i -> Objects.equals(i.getProduct().getId(), productId))
                .findFirst().orElse(null);

        if (existingCartItem == null) {
            throw new IllegalArgumentException("Requested Product is not in shopping cart");

        } else {
            cart.getCartItems().remove(existingCartItem);
            cartItemRepository.delete(existingCartItem);
            cart.setTotalPrice(cart.getCartItems().stream()
                    .mapToDouble(i -> i.getPrice() * i.getQuantity())
                    .reduce(0, Double::sum));

            cartRepository.save(cart);
        }


    }


}
