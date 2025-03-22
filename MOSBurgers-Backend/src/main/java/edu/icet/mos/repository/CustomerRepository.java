package edu.icet.mos.repository;

import edu.icet.mos.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {
//    Optional<CustomerEntity> findByContactNumber(String contactNumber);
//    List<CustomerEntity> findByNameContainingIgnoreCase(String name);


}