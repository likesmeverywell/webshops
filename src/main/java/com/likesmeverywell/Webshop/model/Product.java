package com.likesmeverywell.Webshop.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private String image;

}
