package tn.pfe.spring.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.pfe.spring.DTO.OrderRequestDTO;
import tn.pfe.spring.Entity.Order;
import tn.pfe.spring.Entity.Reservation;
import tn.pfe.spring.Service.OrderService;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrdersController {

    private final OrderService orderService;


    @GetMapping("/getOrderById/{id}")
    public Order getOrderById(@PathVariable(value = "id") Long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/get-all-orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PostMapping("/createOrder")
    public Order createOrder(@Valid @RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @PatchMapping("/change-order-status/{id}") //tested successfully
    public ResponseEntity<Order> changeOrderStatus(@PathVariable("id") Long id, @RequestBody String status) {
        return ResponseEntity.ok().body(orderService.changeOrderStatus(id, status));
    }

    @GetMapping("/get-order-by-user-id") //tested successfully
    public ResponseEntity<List<Order>> getOrderByUserId() {
        return ResponseEntity.ok(orderService.getOrderByUserId());
    }
}


