package com.producer.producermicroservice.manager;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.producer.producermicroservice.exception.ProductNotFoundException;
import com.producer.producermicroservice.repository.ProductRepository;
import com.producer.producermicroservice.vo.Product;

@Service
public class ClientServiceAPI {

	org.slf4j.Logger logger = LoggerFactory.getLogger(ClientServiceAPI.class); 
	
	@Autowired
	private ProductRepository productRepository;
	
	public List<Product> fetchProductsByCategory(String category)  {
		List<Product> productList = null;
		try {
			productList = productRepository.findAll(category);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred during product fetching in mongodb in fetchProductsByCategory");
		}
		
//		if(productList == null || productList.isEmpty()) {
//			throw new  ProductNotFoundException(category);
//		}
		return productList;
	}
}
