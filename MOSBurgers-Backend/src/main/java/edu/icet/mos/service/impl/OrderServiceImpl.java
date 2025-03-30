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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderedProductRepository orderedProductRepository;
    private final ProductRepository productRepository;
    private final ModelMapper mapper;


    @Override
    @Transactional
    public OrderEntity placeOrder(Order order) {
        log.info(String.valueOf(order));

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setDate(new Date(System.currentTimeMillis()));
        orderEntity.setCustomerId(order.getCustomerId());
        orderEntity.setTotalPrice(order.getTotalPrice());

        orderEntity = orderRepository.save(orderEntity);

        List<OrderedProductEntity> orderedProductEntities = new ArrayList<>();
        for (OrderedProduct op : order.getProducts()) {
            OrderedProductEntity orderedProductEntity = new OrderedProductEntity();

            ProductEntity productEntity = productRepository.findById(op.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));


            productEntity.updateStock(op.getQuantity());
            productRepository.save(productEntity);

            orderedProductEntity.setOrder(orderEntity);
            orderedProductEntity.setProduct(productEntity);
            orderedProductEntity.setQuantity(op.getQuantity());
            orderedProductEntity.setDiscount(op.getDiscount());
            orderedProductEntity.setPrice(op.getPrice());

            orderedProductEntities.add(orderedProductEntity);
        }


        orderEntity.setProducts(orderedProductEntities);
        return orderRepository.save(orderEntity);
    }


    @Override
    public List<Order> getAll() {
        List<OrderEntity> orderEntities = orderRepository.findAll();
        List<OrderedProductEntity> orderedProductEntities = orderedProductRepository.findAll();

        List<Order> orders = new ArrayList<>();

        for (OrderEntity order : orderEntities) {

            Order newOrder = mapper.map(order, Order.class);

            for (OrderedProductEntity orderDetail : orderedProductEntities) {
                if (Objects.equals(order.getId(), orderDetail.getId())) {
                    newOrder.getProducts().add(mapper.map(orderDetail, OrderedProduct.class));
                }
            }
            orders.add(newOrder);
        }
        return orders;
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<Order> getAllOrderedProducts(Long orderId) {

        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));


        List<OrderedProductEntity> orderedProductEntities = orderedProductRepository.findByOrderId(orderId);


        Order order = mapper.map(orderEntity, Order.class);


        List<OrderedProduct> orderedProducts = new ArrayList<>();
        for (OrderedProductEntity orderedProductEntity : orderedProductEntities) {
            orderedProducts.add(mapper.map(orderedProductEntity, OrderedProduct.class));
        }

        order.setProducts(orderedProducts);
        return List.of(order);
    }


}