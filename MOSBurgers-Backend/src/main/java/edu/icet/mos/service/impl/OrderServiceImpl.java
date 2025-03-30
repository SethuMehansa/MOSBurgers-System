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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Date;
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
    @Transactional
    public OrderEntity placeOrder(Order order) {
        System.out.println(order);
        // 1. Create and save OrderEntity first
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setDate(new Date(System.currentTimeMillis()));
        orderEntity.setCustomerId(order.getCustomerId());
        orderEntity.setTotalPrice(order.getTotalPrice());

        orderEntity = orderRepository.save(orderEntity); // Save the order first to get an ID

        // 2. Convert ordered products to entities
        List<OrderedProductEntity> orderedProductEntities = new ArrayList<>();
        for (OrderedProduct op : order.getProducts()) {
            OrderedProductEntity orderedProductEntity = new OrderedProductEntity();

            // Fetch the product entity
            ProductEntity productEntity = productRepository.findById(op.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // Update stock
            productEntity.updateStock(op.getQuantity());
            productRepository.save(productEntity);

            // Set order and product references
            orderedProductEntity.setOrder(orderEntity);
            orderedProductEntity.setProduct(productEntity);
            orderedProductEntity.setQuantity(op.getQuantity());
            orderedProductEntity.setDiscount(op.getDiscount());
            orderedProductEntity.setPrice(op.getPrice());

            orderedProductEntities.add(orderedProductEntity);
        }

        // Save ordered products
        orderEntity.setProducts(orderedProductEntities);
        return orderRepository.save(orderEntity); // Save again to persist ordered products
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
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<Order> getAllOrderedProducts(Long orderId) {
        // Fetch the OrderEntity by ID
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Fetch the ordered products for this specific order
        List<OrderedProductEntity> orderedProductEntities = orderedProductRepository.findByOrderId(orderId);

        // Map the order and its ordered products to the Order DTO
        Order order = mapper.map(orderEntity, Order.class);

        // Map the ordered products to OrderedProduct DTO and add them to the order
        List<OrderedProduct> orderedProducts = new ArrayList<>();
        for (OrderedProductEntity orderedProductEntity : orderedProductEntities) {
            orderedProducts.add(mapper.map(orderedProductEntity, OrderedProduct.class));
        }

        order.setProducts(orderedProducts);
        return List.of(order); // Returning a list with one order object containing its ordered products
    }


}