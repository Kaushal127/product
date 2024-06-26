package com.example.product.services;

import com.example.product.dtos.ProductRequestDto;
import com.example.product.dtos.ProductResponseDto;
import com.example.product.dtos.RequestDto;
import com.example.product.exceptions.InvalidProductException;
import com.example.product.models.Category;
import com.example.product.models.Product;
import com.example.product.repositories.CategoryRepository;
import com.example.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("fakeStoreProductService")
public class FakeStoreProductService implements IProductService{
    @Autowired
    RestTemplate restTemplate ;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

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
    public Product getSingleProduct(Long id) throws InvalidProductException {
        // I should pass this id to fakestore and get the details of product
        // "https://fakestoreapi.com/products/1"
        if(id>20){
            throw new InvalidProductException("product with id "+id+" not found");
        }

        ProductResponseDto response = restTemplate.getForObject("https://fakestoreapi.com/products/" + id,
                ProductResponseDto.class);


        return getProductFromResponseDto(response);
    }

    @Override
    public List<Product> getAllProducts() {

        ProductResponseDto[] responseDto =
                restTemplate.getForObject("https://fakestoreapi.com/products",
                                                ProductResponseDto[].class);


        List<Product> products= new ArrayList<>() ;
        for (ProductResponseDto productResponseDto : responseDto){
            products.add(getProductFromResponseDto(productResponseDto));
        }

        return products;
    }

    @Override
    public Product addNewProduct(ProductRequestDto productRequestDto) {
        return null;
    }

    @Override
    public Product updateProduct(Long id, ProductRequestDto productRequestDto) throws InvalidProductException {
        return null;
    }

  /*  @Override
    public Product updateProduct(Long id, ProductRequestDto productRequestDto) {
        RequestCallback requestCallback = restTemplate.httpEntityCallback(productRequestDto , ProductResponseDto.class);
        HttpMessageConverterExtractor<ProductResponseDto> responseExtractor =
                        new HttpMessageConverterExtractor<>(ProductResponseDto.class,
                                restTemplate.getMessageConverters());
        ProductResponseDto productResponseDto = restTemplate.execute("https://fakestoreapi.com/products/" + id,
                HttpMethod.PUT, requestCallback, responseExtractor);

        return getProductFromResponseDto(productResponseDto);
    }*/


    @Override
    public Page<Product> getProductsContainingAName(String name, int pageSize, int startingElementIndex) {
        Page<Product> productPage = productRepository.findByNameContaining(
                name , PageRequest.of(startingElementIndex/pageSize , pageSize , Sort.by("price").ascending())
        ) ;
        return productPage ;
    }

    @Override
    public ResponseEntity<Object> addProduct(RequestDto request) {
        String email = request.getEmail() ;
        Object user = restTemplate.getForObject("http://user/users?email=" + request.getEmail(), Object.class);
        if (user!=null){
            Product product = new Product();
            product.setName(request.getTitle());
            product.setPrice(request.getPrice());
            product.setImage(request.getImage());
            product.setDescription(request.getDescription());

            Optional<Category> category = categoryRepository.findByName(request.getCategory());
            if (category.isEmpty()){
                Category categoryNew = new Category();
                categoryNew = categoryRepository.save(categoryNew);
                product.setCategory(categoryNew);
            } else {
                product.setCategory(category.get());
            }

            Product saveProduct = productRepository.save(product);
            return ResponseEntity.ok(saveProduct);
        } else  {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Status: 500, Message: User with email " + email + " is not present");
        }
    }


}
