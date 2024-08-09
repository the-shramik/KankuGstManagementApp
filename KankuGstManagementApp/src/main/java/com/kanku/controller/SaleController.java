package com.kanku.controller;

import com.kanku.excption.BillNumberAlreadyExistsException;
import com.kanku.model.DateRangeRequest;
import com.kanku.model.batchdto.BatchSaleProduct;
import com.kanku.service.ISaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/sale")
@CrossOrigin("*")
public class SaleController {

    @Autowired
    private ISaleService saleService;

    @PostMapping("/saleProduct")
    public ResponseEntity<?> saleProduct(@RequestBody BatchSaleProduct batchSaleProduct) throws BillNumberAlreadyExistsException {
         return ResponseEntity.ok(saleService.saleProduct(batchSaleProduct));
    }

    @PostMapping("/monthlySaleSummary")
    public ResponseEntity<?> monthlySaleSummary(@RequestBody DateRangeRequest dateRangeRequest){

        return  ResponseEntity.ok(saleService.getMonthlySummary(dateRangeRequest.getStartDate(),dateRangeRequest.getEndDate()));
    }
}
