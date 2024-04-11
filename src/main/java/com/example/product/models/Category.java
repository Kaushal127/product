package com.example.product.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
@Getter
@Setter
public class Category {
    private Long id ;
    private String name ;
}
