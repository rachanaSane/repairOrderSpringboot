package com.auto.repairorder.resource;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auto.repairorder.service.RepairOrderService;
import com.auto.repairorder.view.RepairOrderDto;

@RestController
@RequestMapping(path = "/repair", produces="application/json")
public class RepairOrderResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(RepairOrderResource.class);

	@Autowired
	private RepairOrderService repairOrderService;
	
	/**
	 *  Create Repair order for the farmers
	 * @param repairOrderDto
	 * @return
	 */
	@PostMapping("/order")	
	public RepairOrderDto createRepairOder(@Valid @RequestBody RepairOrderDto repairOrderDto) {
		return repairOrderService.createRepairOder(repairOrderDto);
	}

    /**
     * Get all the repair orders
     * @return
     */
	@GetMapping("/orders")
	public List<RepairOrderDto> getRepairOder() {
		LOGGER.info("Get all the repair orders for the user.");
		return repairOrderService.findAllOrders();
	}

	/**
	 * Get Specific repair order for the Id requested
	 * @param orderId
	 * @return
	 */
	@GetMapping("/order/{orderId}")
	public RepairOrderDto getRepairOder(@PathVariable long orderId) {

		return repairOrderService.getRepairOder(orderId);

	}

	/**
	 * Cancel existing repair order
	 * @param orderId
	 * @return
	 */
	@PatchMapping("/order/{orderId}")
	RepairOrderDto cancelRepairOrder(@PathVariable Long orderId) {
		return repairOrderService.cancelRepairOrder(orderId);
	}

	
}
