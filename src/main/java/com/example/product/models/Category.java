package com.example.product.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@Entity
public class Category extends BaseModel{

    private String description;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private List<Product> products;
}
