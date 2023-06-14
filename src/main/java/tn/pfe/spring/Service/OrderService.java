package tn.pfe.spring.Service;


import tn.pfe.spring.DTO.OrderRequestDTO;
import tn.pfe.spring.Entity.Order;
import tn.pfe.spring.Entity.Reservation;

import java.util.List;


public interface OrderService {

    Order createOrder(Order order);
    Order changeOrderStatus(Long id, String status);
    List<Order> getOrderByUserId();

    Order getOrderById(Long id);
   List<Order> getAllOrders();


}

	
