package com.demoapp.repository;

import com.demoapp.model.subscription.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, Long> {
}
