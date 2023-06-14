package tn.pfe.spring.Service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tn.pfe.spring.DTO.OrderRequestDTO;
import tn.pfe.spring.Entity.*;
import tn.pfe.spring.Repository.OrderRepository;
import tn.pfe.spring.Repository.MenuItemRepository;
import tn.pfe.spring.Repository.OrderRepository;
import tn.pfe.spring.Repository.UserRepository;
import tn.pfe.spring.Service.OrderService;
import tn.pfe.spring.enumeration.ReservationStatus;

import java.time.LocalDateTime;
import java.util.*;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;

    private final UserRepository userRepository;


    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order introuvable"));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order changeOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La order avec l'ID " + id + " n'a pas été trouvée."));
        order.setStatus(OrderStatus.valueOf(status));
        return orderRepository.save(order);
    }
    public List<Order> getOrderByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        AppUser user = userRepository.findByUsername(currentPrincipalName);
        LocalDateTime currentDate = LocalDateTime.now();

        return orderRepository.findAllByCustomer(user);
    }
}

	
