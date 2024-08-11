package com.producer.producermicroservice.exception;

public class ProductNotFoundException extends Exception {

	public ProductNotFoundException(String Id) {
		super(String.format("Not able to fetch Product details: "+Id));
	}
	public ProductNotFoundException(String Id,String err) {
		super(String.format("Not able to fetch Product list due to database exception: "+Id+ "Exception Msg:" +err));
	}
}
