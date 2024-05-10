package com.example.product.services;

import com.example.product.dtos.ProductRequestDto;
import com.example.product.dtos.RequestDto;
import com.example.product.exceptions.InvalidProductException;
import com.example.product.models.Category;
import com.example.product.models.Product;
import com.example.product.repositories.CategoryRepository;
import com.example.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("selfProductService")
public class SelfProductService implements IProductService{

    private ProductRepository productRepository ;
    private CategoryRepository categoryRepository;

    @Autowired
    public SelfProductService(ProductRepository productRepository , CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    @Override
    public Product getSingleProduct(Long id) throws InvalidProductException {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isEmpty()){
            throw new InvalidProductException("Product with id "+ id+" not found");
        }
        Product product = productOptional.get();
        return product;
    }

    @Override
    public Page<Product> getProductsContainingAName(String name, int pageSize, int startingElementIndex) {
        PageRequest pageRequest = PageRequest.of(startingElementIndex/pageSize,pageSize);
        Page<Product> productPage = productRepository.findByNameContaining(name , pageRequest);
        return productPage;
    }

    @Override
    public ResponseEntity<Object> addProduct(RequestDto request) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product addNewProduct(ProductRequestDto productRequestDto) {
        Product product = new Product();
        product.setName(productRequestDto.getTitle());
        product.setPrice(productRequestDto.getPrice());
        product.setDescription(productRequestDto.getDescription());
        product.setImage(productRequestDto.getImage());
        Optional<Category> categoryOptional = categoryRepository.findByName(productRequestDto.getCategory());
        if(categoryOptional.isEmpty()){
            Category category = new Category() ;
            category.setName(productRequestDto.getCategory());
            Category saveCategory = categoryRepository.save(category) ;
            product.setCategory(category);
         } else{
            product.setCategory(categoryOptional.get());
        }
        Product saveProduct = productRepository.save(product);
        return saveProduct;
    }


    @Override
    public Product updateProduct(Long id, ProductRequestDto productRequestDto) throws InvalidProductException {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()){
            throw  new InvalidProductException("Product with id "+ id+ " not present");
        }
        Product existingProduct = productOptional.get();
        Product updateProduct = new Product();
        updateProduct.setId(id);
        updateProduct.setName(
                productRequestDto.getTitle() != null ?
                        productRequestDto.getTitle() :
                        existingProduct.getName()
        );
        updateProduct.setDescription(
                productRequestDto.getDescription() != null ?
                        productRequestDto.getDescription() :
                        existingProduct.getDescription()
        );
        updateProduct.setPrice(
                productRequestDto.getPrice() > 0 ?
                        productRequestDto.getPrice() :
                        existingProduct.getPrice()
        );
        updateProduct.setImage(
                productRequestDto.getImage() != null ?
                        productRequestDto.getImage() :
                        existingProduct.getImage()
        );

        Optional<Category> categoryOptional = categoryRepository.findByName(productRequestDto.getCategory());
        if(categoryOptional.isEmpty()){
            Category category = new Category() ;
            category.setName(productRequestDto.getCategory());
            Category saveCategory = categoryRepository.save(category) ;
            updateProduct.setCategory(category);
        } else{
            updateProduct.setCategory(categoryOptional.get());
        }
        Product saveProduct = productRepository.save(updateProduct);
        return saveProduct;
    }
}
