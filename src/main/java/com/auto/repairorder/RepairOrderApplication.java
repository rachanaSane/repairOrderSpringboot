package com.auto.repairorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("application-context.xml")
public class RepairOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepairOrderApplication.class, args);
	}

}
