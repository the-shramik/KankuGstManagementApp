package com.kanku.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleMonthlySummary {

    private LocalDate saleDate;

    private String saleBillNumber;

    private String paymentType;

    private String sallerGstNumber;

    private Double goodsAmount;

    private Double cGstAmount;

    private Double sGstAmount;

    private Double iGstAmount;

    private Double finalAmount;

    private Double roundUpAmount;
}
