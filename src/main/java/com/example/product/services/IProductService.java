package com.example.product.services;


import com.example.product.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface IProductService {
    public Product getSingleProduct(Long id) ;

    Page<Product> getProductsContainingAName(String name , int pageSize , int startingElementIndex) ;
}
