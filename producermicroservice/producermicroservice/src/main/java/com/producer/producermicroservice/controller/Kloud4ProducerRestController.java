package com.producer.producermicroservice.controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.producer.producermicroservice.exception.ProductNotFoundException;
import com.producer.producermicroservice.manager.ClientServiceAPI;
import com.producer.producermicroservice.vo.Product;

@RestController
public class Kloud4ProducerRestController {

	org.slf4j.Logger logger = LoggerFactory.getLogger(Kloud4ProducerRestController.class); 
	
	@Autowired
	private ClientServiceAPI clientServiceAPI;
	@Autowired
	private Gson gson;
	
	@RequestMapping(path="/fetchProductsUsingCategory/{categoryId}",method = RequestMethod.GET)
	@ResponseBody
	public String fetchProductsUsingCategory(@PathVariable String categoryId) throws ProductNotFoundException {
		logger.info("------------Product List controller called"+categoryId);
		logger.info("-------------Micrometer tracing");
		List<Product> productsData = clientServiceAPI.fetchProductsByCategory(categoryId);
		return gson.toJson(productsData);
	}

}
