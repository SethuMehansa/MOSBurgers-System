package edu.icet.mos.service;

import edu.icet.mos.dto.Order;
import edu.icet.mos.entity.OrderEntity;

import java.util.List;

public interface OrderService {
    OrderEntity placeOrder(Order order);
    List<Order> getAll();
    Long getLastOrderId();
}
