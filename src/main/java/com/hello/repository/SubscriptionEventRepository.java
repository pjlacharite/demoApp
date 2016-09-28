package com.hello.repository;


import com.hello.model.subscription.SubscriptionEvent;
import org.springframework.data.repository.CrudRepository;

public interface SubscriptionEventRepository extends CrudRepository<SubscriptionEvent, Long> {
}
