package edu.icet.mos.repository;

import edu.icet.mos.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {
//    Optional<CustomerEntity> findByContactNumber(String contactNumber);
//    List<CustomerEntity> findByNameContainingIgnoreCase(String name);


}