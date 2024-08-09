package com.kanku.controller;

import com.kanku.model.Supplier;
import com.kanku.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/supplier")
@CrossOrigin("*")
public class SupplierController {

     @Autowired
     private ISupplierService supplierService;

     @PostMapping("/addSupplier")
     public ResponseEntity<?> addSupplier(@RequestBody Supplier supplier){
       return ResponseEntity.ok(supplierService.addSupplier(supplier));
     }

     @GetMapping("/getSuppliers")
     public ResponseEntity<?> getAllSuppliers(){
          return ResponseEntity.ok(supplierService.fetchAllSuppliers());
     }

     @PostMapping("/getSuppliersByName")
     public ResponseEntity<?> getSuppliersByName(@RequestBody Supplier supplier){
          return ResponseEntity.ok(supplierService.fetchAllSuppliersByName(supplier.getSupplierName()));
     }

     @PostMapping("/getSupplierByContact")
     public ResponseEntity<?> getSupplierByContact(@RequestBody Supplier supplier){
          return ResponseEntity.ok(supplierService.fetchSupplierByContact(supplier.getSupplierContact()));
     }

     @GetMapping("/getLastSupplier")
     public ResponseEntity<?> getLastSupplier(){
          return ResponseEntity.ok(supplierService.getLastSupplierInformation());
     }
}
