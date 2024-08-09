package com.kanku.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "product_info")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productCategory;

    private String productName;

    private Double productPrice;

    @Column(unique = true)
    private String productHSNNo;

    private String productUnitType;

    private Boolean isGstApplicable;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "product")
    @JsonIgnore
    private List<Purchase> purchases;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Sale> sales;
}
