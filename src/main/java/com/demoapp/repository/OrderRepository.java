package com.demoapp.repository;

import com.demoapp.model.subscription.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long>{
}
