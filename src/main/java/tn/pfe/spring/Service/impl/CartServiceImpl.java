package tn.pfe.spring.Service.impl;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import tn.pfe.spring.DTO.OrderRequestDTO;
import tn.pfe.spring.Entity.*;

import tn.pfe.spring.Repository.*;
import tn.pfe.spring.Service.CartService;


@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;

    private final UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;


    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart introuvable"));
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }


    public Cart addMenuItemToCart(List<Long> menuItemIds) {
        Cart cart = new Cart();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        AppUser customer = userRepository.findByUsername(currentPrincipalName);
        menuItemIds.forEach(menuItemId -> {

            Optional<MenuItem> menuItemOptional = menuItemRepository.findById(menuItemId);
            if (menuItemOptional.isPresent()) {
                MenuItem menuItem = menuItemOptional.get();
                cart.getItems().add(menuItem);
                // Incrémenter le compteur cartCount du produit
                menuItem.setCartCount(menuItem.getCartCount() + 1);
            } else {
                throw new IllegalArgumentException("Le menu avec l'ID " + menuItemId + " n'existe pas.");
            }
        });
        cart.setCustomer(customer);
        return cartRepository.save(cart);
    }

    public void placeOrderFromCart(OrderRequestDTO orderRequestDTO, Long cartId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        AppUser customer = userRepository.findByUsername(currentPrincipalName);
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart doesnt exist"));
        if (cart != null) {
            List<MenuItem> menuItemsCart = cart.getItems();
            if (menuItemsCart != null && !menuItemsCart.isEmpty()) {
                Order order = new Order();
                order.setCustomer(customer);
                order.setDate(LocalDateTime.now());

                // Calculate the total amount based on the quantity and item prices
                double totalAmount = 0.0;
                Set<OrderItem> orderItems = new HashSet<>();
                for (MenuItem menuItem : menuItemsCart) {
                    double itemPrice = menuItem.getPrice();

                    OrderItem orderItem = new OrderItem();

                    orderItem.setOrder(order);
                    orderItem.setMenuItem(menuItem);

                    orderItems.add(orderItem);
                }

                order.setAmount(totalAmount);
                order.setOrderItems(orderItems);
                order.setStatus(OrderStatus.PENDING); // Set the initial status as pending

                // Save the order and update the customer's cart
                orderRepository.save(order);
                cart.setItems(new ArrayList<>()); // Clear the cart

                userRepository.save(customer);
            } else {
                throw new IllegalStateException("Le panier est vide. Impossible de passer une commande.");
            }
        } else {
            throw new IllegalStateException("Le client n'a pas de panier. Impossible de passer une commande.");
        }
    }

    private double calculateTotalAmount(List<MenuItem> orderItems) {
        double Amount = 0;
        for (MenuItem item : orderItems) {
            Amount += item.getPrice();
        }
        return Amount;
    }

}

	
