package com.kloud4academy.consumer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.kloud4academy.consumer.service.ClientService;
import com.kloud4academy.consumer.vo.ProductInfo;

@Controller
public class HomePageController {
	Logger logger = LoggerFactory.getLogger(HomePageController.class);
	
	@Autowired
	private Gson gson;
	@Autowired
	private ClientService clientService;
	
	@GetMapping({"/home","/"})
	public ModelAndView showHomePage() {
		return callProducerMicroservice();
	}
	
	private ModelAndView callProducerMicroservice() {
		ModelAndView mv = new ModelAndView();
		ResponseEntity<String> recentResponseEntity = null;
		try {
			logger.info("calling kloud4academy micrometer tacing");	
			recentResponseEntity = clientService.fetchProductsUsingCategory("Featured");
			if(recentResponseEntity != null && recentResponseEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
				mv.addObject("apiError", "true");
				mv.setViewName("homepage");
			}
			ResponseEntity<String> featuresResponseEntity = clientService.fetchProductsUsingCategory("RecentProducts");
			ProductInfo[] productInfoList = gson.fromJson(featuresResponseEntity.getBody(), ProductInfo[].class);
			mv.addObject("featureProducts", productInfoList);
			ProductInfo[] recentProducts = gson.fromJson(recentResponseEntity.getBody(), ProductInfo[].class);
			mv.addObject("recentProducts", recentProducts);
		//	mv.addObject("apiError", "true");
			mv.setViewName("homepage");
		} catch (Exception e) {
			mv.addObject("apiError", "true");
			mv.setViewName("homepage");
			logger.error("-----Error Msg:"+e.getMessage());
		}
		 return mv;
	}
}
