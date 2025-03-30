package edu.icet.mos.controller;

import edu.icet.mos.dto.Order;
import edu.icet.mos.entity.OrderEntity;
import edu.icet.mos.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@CrossOrigin
public class OrderController {

    final OrderService orderService;

    @GetMapping("/get-all")
    public List<Order> getAll(){
        return orderService.getAll();
    }
    @PostMapping("/place-order")
    public ResponseEntity<OrderEntity> placeOrder(@RequestBody Order order) {
        OrderEntity savedOrder = orderService.placeOrder(order);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    @GetMapping("/last-order-id")
    public Long getLastOrderId() {
        return orderService.getLastOrderId();
    }
}
