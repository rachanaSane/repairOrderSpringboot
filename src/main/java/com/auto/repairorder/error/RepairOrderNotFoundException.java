package com.auto.repairorder.error;

public class RepairOrderNotFoundException extends RuntimeException {
	
	 public RepairOrderNotFoundException(Long id) {
	        super("Repair order id not found : " + id);
	    }

}
