package tn.pfe.spring.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import tn.pfe.spring.Entity.Cart;
import tn.pfe.spring.Entity.Customer;
import tn.pfe.spring.Entity.MenuItem;
import tn.pfe.spring.Entity.Order;
import tn.pfe.spring.Entity.OrderItem;
import tn.pfe.spring.Entity.OrderStatus;
import tn.pfe.spring.Entity.ResourceNotFoundException;

import tn.pfe.spring.Repository.CartRepository;
import tn.pfe.spring.Repository.CustomerRepository;
import tn.pfe.spring.Repository.MenuItemRepository;
import tn.pfe.spring.Repository.OrderRepository;


@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    


    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart introuvable"));
    }
        

        public void addMenuItemToCart(Long customerId, Long menuItemId) {
            Optional<Customer> customerOptional = customerRepository.findById(customerId);
            if (customerOptional.isPresent()) {
                Customer customer = customerOptional.get();

                Optional<MenuItem> menuItemOptional = menuItemRepository.findById(menuItemId);
                if (menuItemOptional.isPresent()) {
                    MenuItem menuItem = menuItemOptional.get();

                    Cart cart = customer.getCart();
                    if (cart == null) {
                        cart = new Cart();
                        cart.setCustomer(customer);
                        customer.setCart(cart);
                    }

                    List<MenuItem> menuItemsCart = cart.getItems();
                    if (menuItemsCart == null) {
                        menuItemsCart = new ArrayList<>();
                        cart.setItems(menuItemsCart);
                    }

                    menuItemsCart.add(menuItem);
                    
                 // Incrémenter le compteur cartCount du produit
                    menuItem.setCartCount(menuItem.getCartCount() + 1);
                    
                    customerRepository.save(customer);
                } else {
                    throw new IllegalArgumentException("Le menu avec l'ID " + menuItemId + " n'existe pas.");
                }
            } else {
                throw new IllegalArgumentException("Le client avec l'ID " + customerId + " n'existe pas.");
            }
        }
        
        public void placeOrderFromCart(Long customerId, int quantity) {
            Optional<Customer> customerOptional = customerRepository.findById(customerId);
            if (customerOptional.isPresent()) {
                Customer customer = customerOptional.get();
                Cart cart = customer.getCart();
                if (cart != null) {
                    List<MenuItem> menuItemsCart = cart.getItems();
                    if (menuItemsCart != null && !menuItemsCart.isEmpty()) {
                        Order order = new Order();
                        order.setCustomer(customer);
                        order.setQuantity(quantity);
                        order.setDate(LocalDateTime.now());

                        // Calculate the total amount based on the quantity and item prices
                        double totalAmount = 0.0;
                        Set<OrderItem> orderItems = new HashSet<>();
                        for (MenuItem menuItem : menuItemsCart) {
                            double itemPrice = menuItem.getPrice();
                            double itemAmount = itemPrice * quantity;
                            totalAmount += itemAmount;

                            OrderItem orderItem = new OrderItem();
                            orderItem.setQuantity(quantity);
                            
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

                        customerRepository.save(customer);
                    } else {
                        throw new IllegalStateException("Le panier est vide. Impossible de passer une commande.");
                    }
                } else {
                    throw new IllegalStateException("Le client n'a pas de panier. Impossible de passer une commande.");
                }
            } else {
                throw new IllegalArgumentException("Le client avec l'ID " + customerId + " n'existe pas.");
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

	
