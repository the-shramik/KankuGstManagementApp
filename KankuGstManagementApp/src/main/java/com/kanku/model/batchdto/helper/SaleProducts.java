package com.kanku.model.batchdto.helper;

import com.kanku.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleProducts {

    private Product product;
    private Integer productQuantity;
    private Double cGst;
    private Double sGst;
    private Double iGst;
    private Double totalProductSaleAmount;


}
