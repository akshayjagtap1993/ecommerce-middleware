package com.ecommerce.middleware.repository;

import com.ecommerce.middleware.pojo.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {
}
