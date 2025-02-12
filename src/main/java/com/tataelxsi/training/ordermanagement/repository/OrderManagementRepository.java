package com.tataelxsi.training.ordermanagement.repository;

import com.tataelxsi.training.ordermanagement.entity.OrderManagementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderManagementRepository extends JpaRepository<OrderManagementEntity, Long> {

}
