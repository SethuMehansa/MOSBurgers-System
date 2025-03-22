package edu.icet.mos.controller;

import edu.icet.mos.dto.Order;
import edu.icet.mos.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
