package com.auto.repairorder.data;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RepairOrderDeliveredPoller {
	private static final Logger LOGGER=LoggerFactory.getLogger(RepairOrderDeliveredPoller.class);

	public void handleJdbcMessage(List<Map<String, Object>> message) {
	
		
		for (Map<String, Object> resultMap: message) {
			
			LOGGER.info("** Repair order with id "+(Long) resultMap.get("ID") +" has been delivered for repairer "+(String) resultMap.get("REPAIRER_NAME")+" **");
		}
	}
}
