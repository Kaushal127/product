package com.example.product.services;

import com.example.product.dtos.ProductResponseDto;
import com.example.product.models.Category;
import com.example.product.models.Product;
import com.example.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class FakeStoreProductService implements IProductService{
    @Autowired
    RestTemplate restTemplate ;
    @Autowired
    private ProductRepository productRepository;


    public Product getProductFromResponseDto(ProductResponseDto responseDto){
        Product product = new Product();
        product.setId(responseDto.getId());
        product.setName(responseDto.getTitle());
        product.setPrice(responseDto.getPrice());
        product.setDescription(responseDto.getDescription());
        product.setImage(responseDto.getImage());
        product.setCategory(new Category());

        product.getCategory().setName(responseDto.getCategory());
        return product ;
    }
    @Override
    public Product getSingleProduct(Long id){
        // I should pass this id to fakestore and get the details of product
        // "https://fakestoreapi.com/products/1"

        ProductResponseDto response = restTemplate.getForObject("https://fakestoreapi.com/products/" + id,
                ProductResponseDto.class);


        return getProductFromResponseDto(response);
    }

    @Override
    public Page<Product> getProductsContainingAName(String name, int pageSize, int startingElementIndex) {
        Page<Product> productPage = productRepository.findByNameContaining(
                name , PageRequest.of(startingElementIndex/pageSize , pageSize , Sort.by("price").ascending())
        ) ;
        return productPage ;
    }
}
