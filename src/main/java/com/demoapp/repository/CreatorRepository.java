package com.demoapp.repository;

import com.demoapp.model.subscription.Creator;
import org.springframework.data.repository.CrudRepository;

public interface CreatorRepository extends CrudRepository<Creator, String> {
}
