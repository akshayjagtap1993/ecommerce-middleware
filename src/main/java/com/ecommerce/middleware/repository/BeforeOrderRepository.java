package com.ecommerce.middleware.repository;

import com.ecommerce.middleware.pojo.BeforeOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeforeOrderRepository extends CrudRepository<BeforeOrder, Integer> {
}
