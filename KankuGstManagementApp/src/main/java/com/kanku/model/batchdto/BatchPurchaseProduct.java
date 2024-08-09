package com.kanku.model.batchdto;

import com.kanku.model.Supplier;
import com.kanku.model.batchdto.helper.PurchaseProducts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchPurchaseProduct {

     private List<PurchaseProducts> purchaseProducts;

     private Supplier supplier;

     private LocalDate purchaseDate;

     private String billNumber;
}
