package com.example.webshop.service;

import com.example.webshop.exception.NotFoundException;
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
import org.springframework.security.core.context.SecurityContextHolder;
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
                .orElseThrow(() -> new NotFoundException(String.format("Cart with id %s is not found", cartId)));
        return CartMapper.toDto(cart);
    }


    public CartDto addToCart(Long productId, int quantity) {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User loggedUser = userRepository.findByEmail(email);
        Cart cart = getOrCreateCart(loggedUser);
        Product product = getProductById(productId);

        validateAvailableQuantity(product, quantity);

        CartItem existingCartItem = findExistingCartItem(cart, productId);

        if (existingCartItem != null) {
            updateExistingCartItem(existingCartItem, quantity);
        } else {
            createNewCartItem(cart, product, quantity);
        }

        updateCartTotalPrice(cart);
        saveCartAndUser(loggedUser, cart);

        return CartMapper.toDto(cart);
    }


    public CartDto updateCart(Long productId, int quantity) {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User loggedUser = userRepository.findByEmail(email);
        Cart cart = getOrCreateCart(loggedUser);
        Product product = getProductById(productId);
        validateAvailableQuantity(product, quantity);

        CartItem existingCartItem = findExistingCartItem(cart, productId);
        if (existingCartItem == null) {
            throw new NotFoundException("Requested Product is not in the shopping cart");
        }

        if (quantity == 0) {
            removeCartItemFromCart(cart, existingCartItem);
        } else {
            updateCartItemQuantity(existingCartItem, quantity);
        }

        updateCartTotalPrice(cart);
        saveCart(cart);

        return CartMapper.toDto(cart);
    }

    private Cart getOrCreateCart(User user) {
        if (user.getCarts().isEmpty()) {
            Cart cart = new Cart();
            cart.setTotalPrice(0);
            cart.setUser(user);
            return cartRepository.save(cart);
        } else {
            return user.getCarts().get(0);
        }
    }

    private Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(String.format("Product with id %s is not found", productId)));
    }

    private void validateAvailableQuantity(Product product, int quantity) {
        if (product.getAvailableQuantity() < quantity) {
            throw new IllegalArgumentException("Requested quantity exceeds available quantity");
        }
    }

    private CartItem findExistingCartItem(Cart cart, Long productId) {
        return cart.getCartItems().stream()
                .filter(item -> Objects.equals(item.getProduct().getId(), productId))
                .findFirst()
                .orElse(null);
    }

    private void removeCartItemFromCart(Cart cart, CartItem cartItem) {
        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
        updateCartTotalPrice(cart);
        cartRepository.save(cart);
    }

    private void updateCartItemQuantity(CartItem cartItem, int quantity) {
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }

    private void updateCartTotalPrice(Cart cart) {
        double totalPrice = cart.getCartItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        cart.setTotalPrice(totalPrice);
    }

    private void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    private void updateExistingCartItem(CartItem cartItem, int quantity) {
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItem.setPrice(cartItem.getProduct().getPrice());
        cartItemRepository.save(cartItem);
    }

    private void createNewCartItem(Cart cart, Product product, int quantity) {
        CartItem cartItem = CartItem.builder()
                .quantity(quantity)
                .price(product.getPrice())
                .product(product)
                .cart(cart)
                .build();

        cart.getCartItems().add(cartItem);
        cartItemRepository.save(cartItem);
    }


    private void saveCartAndUser(User user, Cart cart) {
        user.getCarts().add(cart);
        userRepository.save(user);
        cartRepository.save(cart);
    }


    public List<CartDto> findCartByUser() {

        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User loggedUser = userRepository.findByEmail(email);

        return cartRepository.findByUser(loggedUser).stream()
                .map(CartMapper::toDto)
                .collect(Collectors.toList());

    }

    public void deleteCart(Long cartId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundException(String.format("Cart with id %s is not found", cartId)));

        cartRepository.delete(cart);
    }

}
