package com.bubble.giju.domain.order.repository;


import com.bubble.giju.domain.order.entity.Order;
import com.bubble.giju.domain.order.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.orderStatus = :status AND o.createdAt < :cutoff AND o.isDeleted = false")
    Page<Order> findByOrderStatusAndCreatedAtBeforeAndIsDeletedFalse(
            @Param("status") OrderStatus status,
            @Param("cutoff") LocalDateTime cutoff,
            Pageable pageable
    );

    @Query("SELECT o.id FROM Order o WHERE o.isDeleted = true AND o.deletedAt < :cutoff")
    List<Long> findIdsBySoftDeletedBefore(@Param("cutoff") LocalDateTime cutoff);

    @Modifying
    @Query("DELETE FROM Order o WHERE o.id IN :ids")
    void hardDeleteByOrderIds(@Param("ids") List<Long> ids);
}




