package edu.icet.mos.controller;

import edu.icet.mos.dto.Order;
import edu.icet.mos.service.OrderService;
import lombok.RequiredArgsConstructor;
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
    public void placeOrder(@RequestBody Order order) {
        System.out.println("Received Order: " + order);
        orderService.addOrder(order);
    }

    @GetMapping("/last-order-id")
    public Long getLastOrderId() {
        return orderService.getLastOrderId();
    }
}
