package com.kanku.model.batchdto;

import com.kanku.model.MyUser;
import com.kanku.model.batchdto.helper.SaleProducts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchSaleProduct {

    private List<SaleProducts> saleProducts;

    private String customerName;

    private String customerAddress;

    private String billNumber;

    private LocalDate saleDate;

    private String sellerGstNumber;

    private MyUser myUser;
}
