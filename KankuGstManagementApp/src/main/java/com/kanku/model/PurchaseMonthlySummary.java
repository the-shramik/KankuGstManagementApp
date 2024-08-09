package com.kanku.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseMonthlySummary {

    private LocalDate purchaseDate;

    private String supplierName;

    private String supplierGstNumber;

    private String billNumber;

    private Double goodsAmount;

    private Double cGst;

    private Double sGst;

    private Double iGst;

    private Double finalAmount;

    private Double roundUpAmount;

}
