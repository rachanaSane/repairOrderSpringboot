package com.auto.repairorder.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import com.auto.repairorder.data.RepairOrderRepository;
import com.auto.repairorder.model.OrderStatus;
import com.auto.repairorder.model.RepairOrder;
import com.auto.repairorder.view.RepairOrderDto;

import ch.qos.logback.core.util.Duration;


//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepairOrderServiceImplTest {
	
	@Autowired
	private RepairOrderService service;
	
	@MockBean
	private RepairOrderRepository repairOrderRepository;
	
	
	@Test
	public void testCreateRepairOrder() {
		
			RepairOrderDto dto = new RepairOrderDto();
			dto.setRepairerName("TestRepairer");
			dto.setStartDateTime(LocalDateTime.of(2019, 1, 1, 00, 00, 00, 00));
			dto.setDuration("02:00:00");
		
		   RepairOrder order = new RepairOrder();		
		   order.setStartDateTime(LocalDateTime.of(2019, 1, 1, 00, 00, 00, 00));
		   order.setRepairerName("testRepairer");
		   order.setOrderStatus("REQUESTED");
		   order.setDuration(Duration.buildByHours(2).getMilliseconds());
		   
		   when(repairOrderRepository.save(order)).thenReturn(order);

		   assertTrue(service.createRepairOder(dto).getRepairerName().equals("testRepairer"));		
		  
	}
	
	@Test
	public void testGetRepairOrder() {
		
		   RepairOrder order = new RepairOrder();
		   order.setId(1l);
		   order.setStartDateTime(LocalDateTime.of(2019, 1, 1, 00, 00, 00, 00));
		   order.setRepairerName("testRepairer");
		   order.setOrderStatus("REQUESTED");
		   order.setDuration(Duration.buildByHours(2).getMilliseconds());
		   
		   when(repairOrderRepository.findById(1l)).thenReturn(Optional.of(order));

		   assertTrue(service.getRepairOder(1l).getRepairerName().equals("testRepairer"));		
		  
	}
	
	@Test
	public void testCancelRepairOrder() {
		
		   RepairOrder order = new RepairOrder();
		   order.setId(1l);
		   order.setStartDateTime(LocalDateTime.of(2019, 1, 1, 00, 00, 00, 00));
		   order.setRepairerName("testRepairer");
		   order.setOrderStatus("REQUESTED");
		   order.setDuration(Duration.buildByHours(2).getMilliseconds());
		   
		   RepairOrder savedorder = new RepairOrder();
		   savedorder.setId(1l);
		   savedorder.setStartDateTime(LocalDateTime.of(2019, 1, 1, 00, 00, 00, 00));
		   savedorder.setRepairerName("testRepairer");
		   savedorder.setOrderStatus("CANCELLED");
		   savedorder.setDuration(Duration.buildByHours(2).getMilliseconds());
		   
		   when(repairOrderRepository.findById(1l)).thenReturn(Optional.of(order));
		   when(repairOrderRepository.save(order)).thenReturn(savedorder);

		   assertTrue(service.cancelRepairOrder(1l).getOrderStatusDescription().equals(OrderStatus.CANCELLED.getDescription()));		
		  
	}
	
	
	@Test
	public void testFindAllRepairOrder() {
		
		   RepairOrder order = new RepairOrder();
		   order.setId(1l);
		   order.setStartDateTime(LocalDateTime.of(2019, 1, 1, 00, 00, 00, 00));
		   order.setRepairerName("testRepairer");
		   order.setOrderStatus("REQUESTED");
		   order.setDuration(Duration.buildByHours(2).getMilliseconds());
		   
		   List<RepairOrder> orders = new ArrayList();
		   orders.add(order);
		   
		   when(repairOrderRepository.findAll()).thenReturn(orders);

		   assertEquals(service.findAllOrders().size(),1);		
		  
	}
	

}
