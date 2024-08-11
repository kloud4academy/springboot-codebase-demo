package com.couchbase.kloud4.couchbase.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

	private String id;
	private String customerName;
	private String Address;
}
