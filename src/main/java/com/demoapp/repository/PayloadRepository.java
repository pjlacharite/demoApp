package com.demoapp.repository;

import com.demoapp.model.subscription.Payload;
import org.springframework.data.repository.CrudRepository;

public interface PayloadRepository extends CrudRepository<Payload, Long>{
}
