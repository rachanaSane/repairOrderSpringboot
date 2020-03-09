package com.auto.repairorder.service;

import java.util.List;



import com.auto.repairorder.view.RepairOrderDto;


public interface RepairOrderService {
	RepairOrderDto createRepairOder(RepairOrderDto repairOrderDto);
	RepairOrderDto getRepairOder(long orderId);
	RepairOrderDto cancelRepairOrder(long orderId);
	List<RepairOrderDto> findAllOrders();
}
