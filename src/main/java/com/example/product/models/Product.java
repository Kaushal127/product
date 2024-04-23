package com.example.product.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.EntityGraph;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class Product extends BaseModel{

    private String description;
    private String image;
    private float price;

    @ManyToOne
    private Category category;
}
