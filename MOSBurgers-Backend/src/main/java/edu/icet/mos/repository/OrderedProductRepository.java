package edu.icet.mos.repository;

import edu.icet.mos.entity.OrderedProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderedProductRepository extends JpaRepository<OrderedProductEntity,Long> {
}
