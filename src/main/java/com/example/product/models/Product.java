package com.example.product.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter

public class Product  {
    private Long id ;
    private String name ;
    private String description ;
    private int price ;
    private String image ;
    private Category category ;

}
