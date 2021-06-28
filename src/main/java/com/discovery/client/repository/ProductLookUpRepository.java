package com.discovery.client.repository;

import com.discovery.client.entity.ProductLookUpEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductLookUpRepository extends MongoRepository<ProductLookUpEntity, String> {

    ProductLookUpEntity findByProductIdOrTitle(String productId, String title);
}
