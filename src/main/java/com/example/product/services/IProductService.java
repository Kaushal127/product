package com.example.product.services;


import com.example.product.dtos.ProductRequestDto;
import com.example.product.dtos.RequestDto;
import com.example.product.exceptions.InvalidProductException;
import com.example.product.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductService {
    public Product getSingleProduct(Long id) throws InvalidProductException;

    Page<Product> getProductsContainingAName(String name , int pageSize , int startingElementIndex) ;

    ResponseEntity<Object> addProduct(RequestDto request);

    List<Product> getAllProducts();

    Product addNewProduct(ProductRequestDto productRequestDto);

    Product updateProduct(Long id, ProductRequestDto productRequestDto) throws InvalidProductException;
}
