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
    public ResponseEntity<List<Order>> getAll() {
        try {
            List<Order> orders = orderService.getAll();
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/place-order")
    public ResponseEntity<OrderEntity> placeOrder(@RequestBody Order order) {
        try {
            OrderEntity savedOrder = orderService.placeOrder(order);
            return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/delete-order/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/get-all-ordered-products-by-order-id/{id}")
    public ResponseEntity<List<Order>> getAllOrderedProducts(@PathVariable Long id) {
        try {
            List<Order> orderedProducts = orderService.getAllOrderedProducts(id);
            return new ResponseEntity<>(orderedProducts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
