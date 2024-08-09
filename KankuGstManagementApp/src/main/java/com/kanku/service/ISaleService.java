package com.kanku.service;

import com.kanku.excption.BillNumberAlreadyExistsException;
import com.kanku.model.Sale;
import com.kanku.model.SaleMonthlySummary;
import com.kanku.model.batchdto.BatchSaleProduct;

import java.time.LocalDate;
import java.util.List;

public interface ISaleService {

     BatchSaleProduct saleProduct(BatchSaleProduct batchSaleProduct) throws BillNumberAlreadyExistsException;

     List<SaleMonthlySummary> getMonthlySummary(LocalDate startDate, LocalDate endDate);
}
