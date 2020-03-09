package com.auto.repairorder.resource;

import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.auto.repairorder.view.RepairOrderDto;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepairOrderResourceTest {
	
	  @LocalServerPort
	    private int port;


	    @Autowired
	    private TestRestTemplate restTemplate;
	    
	    HttpHeaders headers = new HttpHeaders();
	    
	    @Test
	    public void testCreateRepairOder() throws Exception {
	    	  final String baseUrl = "http://localhost:"+ port+"/repair/order";
	          URI uri = new URI(baseUrl);
	          RepairOrderDto dto = new RepairOrderDto();
	          dto.setRepairerName("rach");
	          LocalDateTime date = LocalDateTime.parse("2020-06-01T00:00:00");
	          dto.setStartDateTime(date);
	          dto.setDuration("02:00:00");
	           
	          HttpHeaders headers = new HttpHeaders();
	          headers.set("content-Type", "application/json");      
	   
	          HttpEntity<RepairOrderDto> request = new HttpEntity<>(dto, headers);
	           
	          ResponseEntity<RepairOrderDto> result = restTemplate.postForEntity(uri, request, RepairOrderDto.class);
	           
	          System.out.println("done");
	          //Verify request succeed
	          Assert.assertEquals(200, result.getStatusCodeValue());
	    }    
	    
	    @Test
	    @Order(3)
	    public void getRepairOder() throws Exception {

	        ResponseEntity<String> response = restTemplate.getForEntity(new URL("http://localhost:" + port + "/repair/order/10000200").toString(), String.class);
	        String expectedJson="{\"id\":10000200,\"repairerName\":\"test\",\"startDateTime\":\"2020-05-01 00:00:00\",\"duration\":\"01:00:00\",\"orderStatusDescription\":\"Order has been placed but not yet delivered\"}";
	        // LOGGER.info("----->  : "+response.getBody());
	        JSONAssert.assertEquals(expectedJson, response.getBody(), false);        
	        
	    }
	    
	 
}
