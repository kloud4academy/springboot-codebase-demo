package com.kloud4academy.consumer.service;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class ClientService {
	org.slf4j.Logger logger = LoggerFactory.getLogger(ClientService.class); 
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	
	@CircuitBreaker(name="kloud4breaker",fallbackMethod="fallbackAPIError")
	@Retry(name="kloud4breaker")
	public ResponseEntity<String> fetchProductsUsingCategory(String category) throws Exception {
		List servicesList  = discoveryClient.getServices();
		if(servicesList != null && servicesList.contains("producer-service")) {
			String productApiUrl = "http://producer-service:8082" + "/fetchProductsUsingCategory" + "/"+category;
			RestTemplate restemplate = new RestTemplate();
			ResponseEntity<String> response = restemplate.exchange(productApiUrl,HttpMethod.GET, getHeaders(),String.class);
			return response;
		}
		return null;
	}
	
	private static HttpEntity<?> getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<>(headers);
	}
	
	private ResponseEntity<String> fallbackAPIError(String category,Exception e) {
		logger.info("--------Fallback called circuitbreaker started....");
		return new ResponseEntity<String>("statuscode", HttpStatusCode.valueOf(404));
	}
}
