package com.kanku.service;

import com.kanku.model.Supplier;

import java.util.List;

public interface ISupplierService {

    Supplier addSupplier(Supplier supplier);

    List<Supplier> fetchAllSuppliers();

    List<Supplier> fetchAllSuppliersByName(String name);

    Supplier fetchSupplierByContact(String supplierContact);

    Supplier getLastSupplierInformation();
}
