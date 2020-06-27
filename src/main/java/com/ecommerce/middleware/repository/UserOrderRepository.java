package com.ecommerce.middleware.repository;

import com.ecommerce.middleware.pojo.UserOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOrderRepository extends CrudRepository<UserOrder, Integer> {
}
