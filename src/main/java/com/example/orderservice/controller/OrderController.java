package com.example.orderservice.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.orderservice.constants.RestPathURI;
import com.example.orderservice.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
/**
 * Order Controller class.
 * 
 * @author Srikanth
 *
 */
@Slf4j
@RestController
@RequestMapping(value = RestPathURI.CONTEXT_PATH)
@Api(value = "OrderService")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@ApiOperation(value = "Estimate the delivery date")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Processed successfully", response = LocalDate.class),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal Server Error")})
	@GetMapping(value = RestPathURI.ESTIMATE_ORDER, produces = MediaType.APPLICATION_JSON_VALUE, 
						params = {"localDateTime", "provider"})
	public LocalDate getExpectedDelivery(@RequestParam("localDateTime") 
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime , 
			@RequestParam String provider) {
		return orderService.getExpectedDate(localDateTime, provider);
	}

	
	

}
