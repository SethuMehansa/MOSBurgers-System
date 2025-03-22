package edu.icet.mos.service.impl;

import edu.icet.mos.dto.Order;
import edu.icet.mos.dto.OrderedProduct;
import edu.icet.mos.entity.OrderEntity;
import edu.icet.mos.entity.OrderedProductEntity;
import edu.icet.mos.entity.ProductEntity;
import edu.icet.mos.repository.OrderRepository;
import edu.icet.mos.repository.OrderedProductRepository;
import edu.icet.mos.repository.ProductRepository;
import edu.icet.mos.service.OrderService;
import edu.icet.mos.service.OrderedProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderedProductRepository orderedProductRepository;
    private final ProductRepository productRepository;
    private final ModelMapper mapper;


    @Override
    public void addOrder(Order order) {
        OrderEntity orderEntity = mapper.map(order, OrderEntity.class);
        orderRepository.save(orderEntity);

        for (OrderedProduct orderedProduct : order.getProducts()) {
            OrderedProductEntity orderedProductEntity = mapper.map(orderedProduct, OrderedProductEntity.class);

            ProductEntity productEntity = productRepository.findById(orderedProduct.getProductId())
                    .orElseThrow(() -> new RuntimeException("Item not found with ID: " + orderedProduct.getProductId()));

            productEntity.updateStock(orderedProduct.getQuantity());
            productRepository.save(productEntity);

            orderedProductEntity.setOrder(orderEntity);
            orderedProductEntity.setItem(productEntity);

            orderedProductRepository.save(orderedProductEntity);
        }
    }

    @Override
    public List<Order> getAll() {
        List<OrderEntity> orderEntities = orderRepository.findAll();
        List<OrderedProductEntity> orderedProductEntities = orderedProductRepository.findAll();

        List<Order> orders = new ArrayList<>();

        for (OrderEntity order : orderEntities) {

            Order newOrder = mapper.map(order, Order.class);

            for (OrderedProductEntity orderDetail : orderedProductEntities) {
                if (order.getId() == orderDetail.getId()) {
                    newOrder.getProducts().add(mapper.map(orderDetail, OrderedProduct.class));
                }
            }
            orders.add(newOrder);
        }
        return orders;
    }
}
