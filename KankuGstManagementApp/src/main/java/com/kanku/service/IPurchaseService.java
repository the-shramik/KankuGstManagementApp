package com.kanku.service;

import com.kanku.excption.BillNumberAlreadyExistsException;
import com.kanku.model.Purchase;
import com.kanku.model.PurchaseMonthlySummary;
import com.kanku.model.batchdto.BatchPurchaseProduct;

import java.time.LocalDate;
import java.util.List;

public interface IPurchaseService {

    BatchPurchaseProduct purchaseProduct(BatchPurchaseProduct batchPurchaseProduct) throws BillNumberAlreadyExistsException;

    List<PurchaseMonthlySummary> getMonthlySummary(LocalDate startDate, LocalDate endDate);
}
