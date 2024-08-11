package com.kloud4.kloud4academyHome.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.kloud4.kloud4academyHome.ClientManager.ClientService;

import bo.ProductInfo;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomePageController extends BaseRestController {
	Logger logger = LoggerFactory.getLogger(HomePageController.class);
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	Gson gson;
	
	@GetMapping({"/home","/"})
	public ModelAndView showHomePage(HttpSession session,HttpServletResponse response,HttpServletRequest request) {
		ResponseEntity<String> responseEntity;
		ResponseEntity<String> recentResponseEntity;
		ModelAndView mv = new ModelAndView();
		String cartSize = "0";
		String cartUrl = "";
		String cartId="1111";
   		cartUrl = (String) session.getAttribute("cartUrl");
   		String wishlistSize = (String) session.getAttribute("wishlistSize");
	    if(StringUtils.isBlank(wishlistSize) || wishlistSize.equalsIgnoreCase("0")) {
	    	try {
				wishlistSize = getWishlistCountForOtherPage(session, response, request);
			} catch (Exception e) {
				logger.error("wishlist count call error: "+e.getMessage());
			}
		}
   		if(StringUtils.isBlank(cartUrl)) {
   			clientService.checkandReloadCartCount(session, cartUrl, response, request);
   			cartUrl = (String) session.getAttribute("cartUrl");
   			cartSize = (String) session.getAttribute("cartSize");
   			cartId = (String) session.getAttribute("cartId");
   		} else {
   			cartSize = (String) session.getAttribute("cartSize");
   			//cartId = (String) session.getAttribute("cartId");
   		}
   		mv.addObject("cartId", cartId);
   		mv.addObject("cartUrl", cartUrl);
   		mv.addObject("cartSize", cartSize);
		try {
			 mv.addObject("wishlistSize",wishlistSize);
			responseEntity = clientService.fetchProductsUsingCategory("Featured");
			if(super.checkCircuitBreaker(responseEntity)) {
	    		mv.addObject("apiError", "true");
	    		mv.addObject("error", "true");
	    		mv.setViewName("homepage");
				return mv;
	    	}
			recentResponseEntity = clientService.fetchProductsUsingCategory("RecentProducts");
		    if(responseEntity != null) {
		    	ProductInfo[] productInfoList = gson.fromJson(responseEntity.getBody(), ProductInfo[].class);
		    	mv.addObject("featureProducts", productInfoList);
		    	mv.addObject("error", "");
			}
		    if(recentResponseEntity != null) {
		    	ProductInfo[] recentProducts = gson.fromJson(recentResponseEntity.getBody(), ProductInfo[].class);
		    	mv.addObject("recentProducts", recentProducts);
		    	mv.addObject("error", "");
		    }
		    mv.setViewName("homepage");
		    
		} catch(Exception e) {
			logger.info("-------Products fetching error ----"+e.getMessage());
			mv.addObject("error", "true");
			mv.setViewName("homepage");
		}
		return mv;
	}
}
