package com.ecommerce.middleware.repository;

import com.ecommerce.middleware.pojo.BeforeOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeforeOrderRepository extends CrudRepository<BeforeOrder, Integer> {

    @Query("select bo from com.ecommerce.middleware.pojo.BeforeOrder bo join bo.user u where u.userId=?1 and bo.beforeOrderType=?2")
    public List<BeforeOrder> findByUserIdAndBeforeOrderType(int userId, String beforeOrderType);

    @Query("select bo from com.ecommerce.middleware.pojo.BeforeOrder bo join bo.user u join bo.product p where u.userId=?1 and p.productId=?2")
    public BeforeOrder findByUserIdAndProductId(int userId, int productId);
}
