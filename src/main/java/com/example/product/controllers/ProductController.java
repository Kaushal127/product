package com.example.product.controllers;

import com.example.product.dtos.ProductRequestDto;
import com.example.product.dtos.ProductResponseDto;
import com.example.product.dtos.RequestDto;
import com.example.product.exceptions.InvalidProductException;
import com.example.product.models.Category;
import com.example.product.models.Product;
import com.example.product.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
public class ProductController {


    private IProductService productService ;

    @Autowired
    public ProductController(@Qualifier("selfProductService") IProductService productService){
        this.productService=productService;
    }

    // Get all the products
    @GetMapping("/products")
    public List<Product>  getAllProducts(){

        return productService.getAllProducts() ;
    }

    // Get product with id
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getSingleProduct(@PathVariable("id") Long id) {
        ResponseEntity<Product> response ;
        try {
            Product product =  productService.getSingleProduct(id) ;
            response = new ResponseEntity<>(product, HttpStatus.OK);
        } catch (InvalidProductException e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @GetMapping("/products/search")
    public Page<Product> getProductByName(@RequestParam("name") String name ,
                                 @RequestParam("pageSize") int pageSize ,
                                 @RequestParam("startingElementIndex")  int startingElementIndex){
        return productService.getProductsContainingAName(name,pageSize,startingElementIndex) ;
    }

    // add a product
    @PostMapping("/products")
    public Product addProduct(@RequestBody ProductRequestDto productRequestDto){
        return productService.addNewProduct(productRequestDto);
    }

    // update a product
    @PutMapping("/products/{id}")
    public Product updatedProduct(@PathVariable("id") Long id ,
                                  @RequestBody ProductRequestDto productRequestDto) throws InvalidProductException {

        return productService.updateProduct(id,productRequestDto);
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
