package com.couchbase.kloud4.couchbase.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class CouchBaseController {

	  @Autowired
	  private Kloud4CouchbaseConfiguration couchBaseConfiguration;
	  
	  @GetMapping("/couchbasecacheput")
	  public void couchbasecacheputMethod() {
		  
		  Customer customer = new Customer("kloud4111", "Arivalagan", "Bangalore");
		  Customer customer1 = new Customer("kloud4222", "Arivalagan1", "Chennai");
		  Customer customer2 = new Customer("kloud4333", "Arivalagan2", "Odissa");
		  Customer customer3 = new Customer("kloud4444", "Arivalagan3", "Delhi");
		  Customer customer4 = new Customer("kloud4555", "Arivalagan4", "Mumbai");
		  
		  couchBaseConfiguration.couchbaseTemplate().upsertById(Customer.class).one(customer);
		  couchBaseConfiguration.couchbaseTemplate().upsertById(Customer.class).one(customer1);
		  couchBaseConfiguration.couchbaseTemplate().upsertById(Customer.class).one(customer2);
		  couchBaseConfiguration.couchbaseTemplate().upsertById(Customer.class).one(customer3);
		  couchBaseConfiguration.couchbaseTemplate().upsertById(Customer.class).one(customer4);
	  }
	  
	  
	  @GetMapping("/couchbasecacheretryval/{cacheId}")
	  public Customer getCouchCache(@PathVariable String cacheId) {
		  Customer found = null;
		  if(couchBaseConfiguration.couchbaseTemplate().existsById(Customer.class).one(cacheId)) {
			  found = couchBaseConfiguration.couchbaseTemplate().findById(Customer.class).one(cacheId);
		  }
		  
		  return found;
	  }
	  
	  @GetMapping("/couchbasecachedelete/{cacheId}")
	  public Customer couchbasecachedeleteOp(@PathVariable String cacheId) {
		  Customer found = null;
		  if(couchBaseConfiguration.couchbaseTemplate().existsById(Customer.class).one(cacheId)) {
			  couchBaseConfiguration.couchbaseTemplate().removeById(Customer.class).one(cacheId);
		  }
		  
		  return found;
	  }
}
