package com.discovery.client.repository;

import com.discovery.client.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends MongoRepository<ProductEntity, String> {
}
