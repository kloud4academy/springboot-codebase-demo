package com.kloud4academy.consumer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Kloud4academyRestController {

	@GetMapping(path="/getkloud4test")
	public String getKloud4Test() {
		return "Hello World";
	}
}
