package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.driver.model.request.OrderDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.OrderDetailsResponse;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.RequestOperationStatus;
import com.driver.service.impl.OrderServiceImpl;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	OrderServiceImpl orderServiceImpl;

	@GetMapping(path="/{id}")
	public OrderDetailsResponse getOrder(@PathVariable String id) throws Exception{

			OrderDto orderDto = orderServiceImpl.getOrderById(id);

			OrderDetailsResponse response = new OrderDetailsResponse();

			response.setOrderId(orderDto.getOrderId());
			response.setUserId(orderDto.getUserId());
			response.setStatus(orderDto.isStatus());
			response.setItems(orderDto.getItems());
			response.setCost(orderDto.getCost());

			return response;
	}
	
	@PostMapping()
	public OrderDetailsResponse createOrder(@RequestBody OrderDetailsRequestModel order) {
		
			OrderDto orderDto = new OrderDto();

			orderDto.setOrderId(String.valueOf(UUID.randomUUID()));
			orderDto.setUserId(order.getUserId());
			orderDto.setStatus(true);
			orderDto.setItems(orderDto.getItems());
			orderDto.setCost(orderDto.getCost());

			OrderDto savedDto = orderServiceImpl.createOrder(orderDto);

			OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();
			orderDetailsResponse.setUserId(savedDto.getUserId());
			orderDetailsResponse.setOrderId(savedDto.getOrderId());
			orderDetailsResponse.setStatus(savedDto.isStatus());
			orderDetailsResponse.setItems(savedDto.getItems());
			orderDetailsResponse.setCost(savedDto.getCost());

			return orderDetailsResponse;
	}
		
	@PutMapping(path="/{id}")
	public OrderDetailsResponse updateOrder(@PathVariable String id, @RequestBody OrderDetailsRequestModel order) throws Exception{
		
			OrderDto orderDto = new OrderDto();

			orderDto.setOrderId(id);
			orderDto.setUserId(order.getUserId());
			orderDto.setStatus(true);
			orderDto.setItems(order.getItems());
			orderDto.setCost(order.getCost());

			OrderDto savedDto = orderServiceImpl.updateOrderDetails(id,orderDto);

			OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();

			orderDetailsResponse.setUserId(savedDto.getUserId());
			orderDetailsResponse.setStatus(savedDto.isStatus());
			orderDetailsResponse.setOrderId(savedDto.getOrderId());
			orderDetailsResponse.setItems(savedDto.getItems());
			orderDetailsResponse.setCost(savedDto.getCost());

			return orderDetailsResponse;
	}
	
	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteOrder(@PathVariable String id) throws Exception {
		
		try{
			orderServiceImpl.deleteOrder(id);
			OperationStatusModel operationStatusModel = new OperationStatusModel();
			operationStatusModel.setOperationName(RequestOperationName.DELETE.name());
			operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());
			return  operationStatusModel;
		}
		catch (Exception e){
			OperationStatusModel operationStatusModel = new OperationStatusModel();
			operationStatusModel.setOperationName(RequestOperationName.DELETE.name());
			operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.name());
			return  operationStatusModel;
		}
	}
	
	@GetMapping()
	public List<OrderDetailsResponse> getOrders() {
		List<OrderDto> orderDtoList = orderServiceImpl.getOrders();

		List<OrderDetailsResponse> ans = new ArrayList<>();

		for (OrderDto x : orderDtoList) {

			OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();

			orderDetailsResponse.setUserId(x.getUserId());
			orderDetailsResponse.setOrderId(x.getOrderId());
			orderDetailsResponse.setStatus(x.isStatus());
			orderDetailsResponse.setItems(x.getItems());
			orderDetailsResponse.setCost(x.getCost());

			ans.add(orderDetailsResponse);

		}

		return ans;

	}
}
