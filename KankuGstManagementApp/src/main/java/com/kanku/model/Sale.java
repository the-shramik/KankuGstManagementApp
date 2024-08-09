package com.kanku.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sale_info")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saleId;

    private String saleBillNo;;

    private  Integer saleQuantity;


    private Double totalProductSaleAmount;

    private LocalDate saleDate;

    private Double cGST;

    private Double sGST;

    private Double iGST;

    private String customerName;

    private String customerAddress;

    private String sellerGstNumber;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


}
