package com.auto.repairorder.data;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auto.repairorder.model.RepairOrder;




@Repository
public interface RepairOrderRepository extends JpaRepository<RepairOrder, Long> {
	
	
	@Query("SELECT order FROM RepairOrder order WHERE order.orderStatus not in('DELIVERED','CANCELLED') AND order.endDateTime >= :newStartDate AND order.startDateTime <= :newEndDate")
	Collection<RepairOrder> findAllOverlappedRepairOrders(@Param("newStartDate") LocalDateTime startDate, @Param("newEndDate") LocalDateTime endDate );

}
