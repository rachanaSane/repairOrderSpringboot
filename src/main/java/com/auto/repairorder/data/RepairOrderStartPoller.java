package com.auto.repairorder.data;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;


@Component
public class RepairOrderStartPoller {
	private static final Logger LOGGER=LoggerFactory.getLogger(RepairOrderStartPoller.class);

	@ServiceActivator
	public void handleJdbcMessage(List<Map<String, Object>> message) {
		LOGGER.info("inside RepairOrderStartPoller handleJdbcMessage");
		for (Map<String, Object> resultMap: message) {
			
			LOGGER.info("** Repair order with id "+(Long) resultMap.get("ID") +" has started for farm "+(String) resultMap.get("REPAIRER_NAME")+" **");
		}
	}
}
