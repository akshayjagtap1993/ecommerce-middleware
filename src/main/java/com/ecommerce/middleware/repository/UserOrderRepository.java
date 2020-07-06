package com.ecommerce.middleware.repository;

import com.ecommerce.middleware.pojo.UserOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserOrderRepository extends CrudRepository<UserOrder, Integer> {

    @Query("select o from com.ecommerce.middleware.pojo.UserOrder o join o.user u where u.userId=?1")
    public List<UserOrder> findByUserId(int userId);

    @Query("select o from com.ecommerce.middleware.pojo.UserOrder o join o.user u join o.product p where u.userId=?1 and p.productId=?2")
    public List<UserOrder> findByUserIdAndProductId(int userId, int productId);
}
