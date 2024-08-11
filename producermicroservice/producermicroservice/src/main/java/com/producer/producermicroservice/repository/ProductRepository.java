package com.producer.producermicroservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.producer.producermicroservice.vo.Product;

public interface ProductRepository  extends MongoRepository<Product,String>{ 
	@Query(value="{category:'?0'}")
	List<Product> findAll(String category);
	public long count();
}
