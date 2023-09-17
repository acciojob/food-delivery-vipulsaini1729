package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.driver.model.request.FoodDetailsRequestModel;
import com.driver.model.response.FoodDetailsResponse;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.RequestOperationStatus;
import com.driver.service.impl.FoodServiceImpl;
import com.driver.shared.dto.FoodDto;
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
@RequestMapping("/foods")
public class FoodController {

	@Autowired
	FoodServiceImpl foodServiceImpl;

	@GetMapping(path="/{id}")
	public FoodDetailsResponse getFood(@PathVariable String id) throws Exception{

		FoodDto foodDto = foodServiceImpl.getFoodById(id);

		FoodDetailsResponse response = new FoodDetailsResponse();

		response.setFoodId(foodDto.getFoodId());
		response.setFoodName(foodDto.getFoodName());
		response.setFoodCategory(foodDto.getFoodCategory());
		response.setFoodPrice(foodDto.getFoodPrice());

		return response;
	}

	@PostMapping("/create")
	public FoodDetailsResponse createFood(@RequestBody FoodDetailsRequestModel foodDetails) {

		FoodDto foodDto = new FoodDto();
		foodDto.setFoodId(String.valueOf(UUID.randomUUID()));
		foodDto.setFoodCategory(foodDetails.getFoodCategory());
		foodDto.setFoodName(foodDetails.getFoodName());
		foodDto.setFoodPrice(foodDetails.getFoodPrice());

		FoodDto responseDto = foodServiceImpl.createFood(foodDto);

		FoodDetailsResponse response = new FoodDetailsResponse();

		response.setFoodId(responseDto.getFoodId());
		response.setFoodName(responseDto.getFoodName());
		response.setFoodCategory(responseDto.getFoodCategory());
		response.setFoodPrice(responseDto.getFoodPrice());

		return response;

	}

	@PutMapping(path="/{id}")
	public FoodDetailsResponse updateFood(@PathVariable String id, @RequestBody FoodDetailsRequestModel foodDetails) throws Exception{

		FoodDto foodDto = new FoodDto();

		foodDto.setFoodId(id);
		foodDto.setFoodName(foodDetails.getFoodName());
		foodDto.setFoodCategory(foodDetails.getFoodCategory());
		foodDto.setFoodPrice(foodDetails.getFoodPrice());

		FoodDto response = foodServiceImpl.updateFoodDetails(id, foodDto);

		FoodDetailsResponse foodDetailsResponse = new FoodDetailsResponse();

		foodDetailsResponse.setFoodId(response.getFoodId());
		foodDetailsResponse.setFoodName(response.getFoodName());
		foodDetailsResponse.setFoodCategory(response.getFoodCategory());
		foodDetailsResponse.setFoodPrice(response.getFoodPrice());

		return foodDetailsResponse;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteFood(@PathVariable String id) throws Exception{

		try{
			foodServiceImpl.deleteFoodItem(id);

			OperationStatusModel operationStatusModel = new OperationStatusModel();
			operationStatusModel.setOperationName(RequestOperationName.DELETE.name());
			operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());
			return operationStatusModel;
		}
		catch (Exception e){
			OperationStatusModel operationStatusModel = new OperationStatusModel();
			operationStatusModel.setOperationName(RequestOperationName.DELETE.name());
			operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.name());
			return operationStatusModel;
		}
	}
	
	@GetMapping()
	public List<FoodDetailsResponse> getFoods() {
		List<FoodDto> foodDto = foodServiceImpl.getFoods();

		List<FoodDetailsResponse> ans = new ArrayList<>();

		for (FoodDto x: foodDto) {
			FoodDetailsResponse foodDetailsResponse = new FoodDetailsResponse();

			foodDetailsResponse.setFoodId(x.getFoodId());
			foodDetailsResponse.setFoodCategory(x.getFoodCategory());
			foodDetailsResponse.setFoodName(x.getFoodName());
			foodDetailsResponse.setFoodPrice(x.getFoodPrice());

			ans.add(foodDetailsResponse);
		}
		return ans;
	}
}
