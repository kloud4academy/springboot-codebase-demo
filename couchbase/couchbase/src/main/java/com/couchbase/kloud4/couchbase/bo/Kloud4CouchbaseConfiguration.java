package com.couchbase.kloud4.couchbase.bo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.CouchbaseClientFactory;
import org.springframework.data.couchbase.SimpleCouchbaseClientFactory;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.couchbase.core.convert.MappingCouchbaseConverter;

import com.couchbase.client.core.env.PasswordAuthenticator;

@Configuration
public class Kloud4CouchbaseConfiguration {
	
	 @Bean
	  public CouchbaseTemplate couchbaseTemplate() {
			 return new CouchbaseTemplate(myCouchbaseClientFactory("kloudacademy"), new MappingCouchbaseConverter());
	 }
	  
	  public CouchbaseClientFactory myCouchbaseClientFactory(String bucketName) {
		  com.couchbase.client.core.env.Authenticator authenticater = PasswordAuthenticator.create("testcouchdbdemo", "Welcome@123");
		  return new SimpleCouchbaseClientFactory("couchbases://cb.bxnd5ervjct-ttqa.cloud.couchbase.com",authenticater, bucketName );
	  }

}
