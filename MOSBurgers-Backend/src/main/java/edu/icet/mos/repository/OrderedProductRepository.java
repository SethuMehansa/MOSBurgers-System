package edu.icet.mos.repository;

import edu.icet.mos.entity.OrderedProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderedProductRepository extends JpaRepository<OrderedProductEntity,Long> {
    List<OrderedProductEntity> findByOrderId(Long orderId);
}
