package com.example.product.controllers;

import com.example.product.dtos.ProductResponseDto;
import com.example.product.dtos.RequestDto;
import com.example.product.models.Product;
import com.example.product.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private IProductService productService ;

    // Get all the products
    @GetMapping("/products")
    public List<Product>  getAllProducts(){

        return new ArrayList<>() ;
    }

    // Get product with id
    @GetMapping("/products/{id}")
    public Product getSingleProduct(@PathVariable("id") Long id) {
        return productService.getSingleProduct(id) ;
    }

    @GetMapping("/products/search")
    public Page<Product> getProductByName(@RequestParam("name") String name ,
                                 @RequestParam("pageSize") int pageSize ,
                                 @RequestParam("startingElementIndex")  int startingElementIndex){
        return productService.getProductsContainingAName(name,pageSize,startingElementIndex) ;
    }

    // add a product
    @PostMapping("/products")
    public Product addProduct(@RequestBody ProductResponseDto productResponseDto){
        return new Product() ;
    }

    // update a product
    @PutMapping("/products/{id}")
    public Product updatedProduct(@PathParam("id") Long id ,
                                  @RequestBody ProductResponseDto productResponseDto){
        return new Product();
    }

    // delete a product
    @DeleteMapping("/products/{id}")
    public boolean deleteProduct(@PathParam("id") Long id){
        return true ;
    }

    @PostMapping("/dummyProducts")
    public ResponseEntity<Object> addProduct(@RequestBody RequestDto request){
        return productService.addProduct(request);
    }

}
