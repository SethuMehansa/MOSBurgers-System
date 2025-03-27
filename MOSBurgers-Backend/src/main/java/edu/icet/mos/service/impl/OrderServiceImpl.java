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
import java.util.stream.Collectors;

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
    public void addOrder(Order order) {
        System.out.println("Received Order DTO: " + order);

        OrderEntity orderEntity = mapper.map(order, OrderEntity.class);

        // Ensure the list is initialized
        if (orderEntity.getProducts() == null) {
            orderEntity.setProducts(new ArrayList<>());
        }

        System.out.println("Mapped OrderEntity: " + orderEntity);

        List<OrderedProductEntity> orderedProductEntities = new ArrayList<>();

        if (order.getProducts() == null || order.getProducts().isEmpty()) {
            throw new RuntimeException("Order products are null or empty!");
        }

        for (OrderedProduct orderedProduct : order.getProducts()) {
            System.out.println("Processing Ordered Product: " + orderedProduct);

            OrderedProductEntity orderedProductEntity = mapper.map(orderedProduct, OrderedProductEntity.class);

            ProductEntity product = productRepository.findById(orderedProduct.getProductId())
                    .orElseThrow(() -> new RuntimeException(orderedProduct.getProductId() + " Not Found!"));

            product.updateStock(orderedProduct.getQuantity());
            productRepository.save(product);

            orderedProductEntity.setOrder(orderEntity);
            orderedProductEntity.setProduct(product);

            orderedProductEntities.add(orderedProductEntity);
        }

        orderEntity.setProducts(orderedProductEntities);

        System.out.println("Final OrderEntity before saving: " + orderEntity);

        orderRepository.save(orderEntity);
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

    @Override
    public Long getLastOrderId() {
        List<OrderEntity> orderEntities = orderRepository.findAll();
        if (orderEntities.isEmpty()) {
            return 100001L;
        }
        Long lastOrderId = 100001L;
        for (OrderEntity order : orderEntities) {
            if (order.getId() > lastOrderId) {
                lastOrderId = order.getId();
            }
        }
        return lastOrderId;
    }
}