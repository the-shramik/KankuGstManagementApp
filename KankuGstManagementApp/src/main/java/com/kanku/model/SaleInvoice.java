package com.kanku.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleInvoice {

       private String customerAddress;

       private String userAddress;

       private String billNumber;
       
       private String date;
       
       private Double cGST;
       
       private Double sGST;
       
       private Double iGST;
       
       private String productHSNNo;
       
       private Double productQuantity;
       
       private Double productPrice;
       
       private String productUnitType;
       
       private Double goodsAmount;
       
       private Double finalAmount;
       
       private String bankName;
       
       private String bankAccNo;
       
       private String bankBranch;
       
       private String bankIFSCCode;
}
