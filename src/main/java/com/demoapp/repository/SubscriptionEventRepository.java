package com.demoapp.repository;


import com.demoapp.model.subscription.SubscriptionEvent;
import org.springframework.data.repository.CrudRepository;

public interface SubscriptionEventRepository extends CrudRepository<SubscriptionEvent, Long> {
}
