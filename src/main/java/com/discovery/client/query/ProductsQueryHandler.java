package com.discovery.client.query;

import com.discovery.client.entity.ProductEntity;
import com.discovery.client.repository.ProductsRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductsQueryHandler {
    private final ProductsRepository productsRepository;

    public ProductsQueryHandler(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @QueryHandler
    public List<ProductRestModel> getProducts(FindProductsQuery query) {
        List<ProductRestModel> productRestModels = new ArrayList<>();
        List<ProductEntity> productEntities = productsRepository.findAll();
        for(ProductEntity productEntity: productEntities){
            ProductRestModel productRestModel = new ProductRestModel();
            BeanUtils.copyProperties(productEntity,productRestModel);
            productRestModels.add(productRestModel);
        }
       return productRestModels;
    }
}
