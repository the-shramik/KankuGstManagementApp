package com.kanku.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "purchase_info")
public class Purchase {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long purchaseId;

     private String purchaseBillNo;

     private Integer productQuantity;

     private Double totalPurchaseAmount;

     private LocalDate purchaseDate;

     private Double cGST;

     private Double sGST;

     private Double iGST;

     private String supplierGstNumber;

     @ManyToOne
     @JoinColumn(name="product_id")
     private Product product;

     @ManyToOne
     @JoinColumn(name="supplier_id")
     private Supplier supplier;

     
  }
