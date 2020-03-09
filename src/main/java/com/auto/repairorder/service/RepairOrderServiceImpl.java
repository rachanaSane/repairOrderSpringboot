package com.auto.repairorder.service;

import com.auto.repairorder.data.RepairOrderRepository;
import com.auto.repairorder.error.RepairOrderException;
import com.auto.repairorder.error.RepairOrderNotFoundException;
import com.auto.repairorder.model.OrderStatus;
import com.auto.repairorder.model.RepairOrder;
import com.auto.repairorder.view.RepairOrderDto;





import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepairOrderServiceImpl implements RepairOrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RepairOrderServiceImpl.class);

	@Autowired
	private RepairOrderRepository repairOrderRepository;

	@Override
	public RepairOrderDto createRepairOder(RepairOrderDto repairOrderDto) {

		RepairOrder repairOrder = convertToEntity(repairOrderDto);
		validateTimeOverlap(repairOrder);
		repairOrder.setOrderStatus(OrderStatus.REQUESTED.toString());
		RepairOrder createdRepairOrder = repairOrderRepository.save(repairOrder);
		LOGGER.info("** New order has been placed for repairer " + createdRepairOrder.getRepairerName() + " with orderId:"
				+ createdRepairOrder.getId() + " **");
		return convertToDto(createdRepairOrder);

	}

	@Override
	public RepairOrderDto getRepairOder(long orderId) {
		return repairOrderRepository.findById(orderId).map(x -> {
			return convertToDto(x);

		}).orElseThrow(() -> new RepairOrderNotFoundException(orderId));
	}

	@Override
	public RepairOrderDto cancelRepairOrder(long orderId) {

		return repairOrderRepository.findById(orderId).map(x -> {
			return performCancelOrder(x);
		}).orElseGet(() -> {
			throw new RepairOrderNotFoundException(orderId);
		});

	}

	@Override
	public List<RepairOrderDto> findAllOrders() {

		return repairOrderRepository.findAll().stream().map(x -> {
			return convertToDto(x);
		}).collect(Collectors.toList());

	}

	/**
	 * Convert RepairOrder entity object to RepairOrderDto view object. Entity object
	 * has extra parameters than view object.
	 * 
	 * @param RepairOrder
	 * @return
	 */
	private RepairOrderDto convertToDto(RepairOrder repairOrder) {

		RepairOrderDto repairOrderDto = new RepairOrderDto();
		repairOrderDto.setId(repairOrder.getId());
		repairOrderDto.setRepairerName(repairOrder.getRepairerName());
		repairOrderDto.setStartDateTime(repairOrder.getStartDateTime());
		repairOrderDto.setDuration(convertToDuration(repairOrder.getDuration()));
		repairOrderDto.setOrderStatusDescription(OrderStatus.valueOf(repairOrder.getOrderStatus()).getDescription());// Show
																													// user
																													// description
																													// and
																													// not
																													// the
																													// status

		return repairOrderDto;

	}

	/**
	 * Convert RepairOrderDto view object to RepairOrderDto view object.
	 * 
	 * @param RepairOrderDto
	 * @return
	 */
	private RepairOrder convertToEntity(RepairOrderDto repairOrderDto) {
		RepairOrder repairOrder = new RepairOrder();

		repairOrder.setRepairerName(repairOrderDto.getRepairerName());
		repairOrder.setStartDateTime(repairOrderDto.getStartDateTime());

		// convert string duration received to miliseconds
		repairOrder.setDuration(convertToMiliseconds(repairOrderDto.getDuration()));

		// calculate end date and add to start date
		repairOrder.setEndDateTime(repairOrderDto.getStartDateTime().plus(repairOrder.getDuration(), ChronoUnit.MILLIS));

		return repairOrder;
	}

	/**
	 * Make sure repair order received do not overlap with existing order.
	 * 
	 * @param repairOrder
	 */
	private void validateTimeOverlap(RepairOrder repairOrder) {
		Collection<RepairOrder> orders = repairOrderRepository.findAllOverlappedRepairOrders(repairOrder.getStartDateTime(),
				repairOrder.getEndDateTime());
		if (!orders.isEmpty()) {
			throw new RepairOrderException(
					"Other Repair order is in progress during this time. Please choose another duration");
		}
	}

	/**
	 * Convert string duration in format HH:MM:SS to miliseconds for better
	 * calculations
	 * 
	 * @param duration
	 * @return
	 */
	private long convertToMiliseconds(String duration) {

		String[] tokens = duration.split(":");
		int secondsToMs = Integer.parseInt(tokens[2]) * 1000;
		int minutesToMs = Integer.parseInt(tokens[1]) * 60000;
		int hoursToMs = Integer.parseInt(tokens[0]) * 3600000;
		return secondsToMs + minutesToMs + hoursToMs;

	}

	/**
	 * Convert duration in miliseconds to String with format HH:MM:SS
	 * 
	 * @param duration
	 * @return
	 */
	private String convertToDuration(long duration) {
		return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(duration),
				TimeUnit.MILLISECONDS.toMinutes(duration)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration)),
				TimeUnit.MILLISECONDS.toSeconds(duration)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));

	}

	private RepairOrderDto performCancelOrder(RepairOrder repairOrder) {
		if (OrderStatus.DELIVERED.toString().equals(repairOrder.getOrderStatus())) {
			throw new RepairOrderException("Cancellation is not possible. Order is already delivered. ");
		} else {
			repairOrder.setOrderStatus(OrderStatus.CANCELLED.toString());
			RepairOrder updatedRepairOrder = repairOrderRepository.save(repairOrder);
			LOGGER.info("** Repair order has been cancelled for id : " + repairOrder.getId() + " **");
			return convertToDto(updatedRepairOrder);
		}
	}

}
