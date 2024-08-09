package com.kanku.controller;

import com.kanku.excption.BillNumberAlreadyExistsException;
import com.kanku.model.DateRangeRequest;
import com.kanku.model.batchdto.BatchPurchaseProduct;
import com.kanku.service.IPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchase")
@CrossOrigin("*")
public class PurchaseController {

    @Autowired
    private IPurchaseService purchaseService;

    @PostMapping("/purchaseProduct")
    public ResponseEntity<?> purchaseProduct(@RequestBody BatchPurchaseProduct batchPurchaseProduct) throws BillNumberAlreadyExistsException {

        System.out.println("Bill Number=====>"+batchPurchaseProduct.getBillNumber());
        return  ResponseEntity.ok(purchaseService.purchaseProduct(batchPurchaseProduct));
    }

    @PostMapping("/monthlyPurchaseSummary")
    private ResponseEntity<?> monthlyPurchaseSummary(@RequestBody DateRangeRequest dateRangeRequest){
        System.out.println(dateRangeRequest);
         return ResponseEntity.ok(purchaseService.getMonthlySummary(dateRangeRequest.getStartDate(),dateRangeRequest.getEndDate()));
    }
}
