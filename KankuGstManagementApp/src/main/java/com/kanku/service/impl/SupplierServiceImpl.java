package com.kanku.service.impl;

import com.kanku.dao.ISupplierRepository;
import com.kanku.model.Supplier;
import com.kanku.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements ISupplierService {

    @Autowired
    private ISupplierRepository supplierRepository;

    @Override
    public Supplier addSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public List<Supplier> fetchAllSuppliers() {
        return supplierRepository.findAll();
    }

    @Override
    public List<Supplier> fetchAllSuppliersByName(String name) {
        return supplierRepository.getSupplierBySupplierName(name);
    }

    @Override
    public Supplier fetchSupplierByContact(String supplierContact) {
        Optional<Supplier> optional = supplierRepository.getSupplierBySupplierContact(supplierContact);
        return optional.orElse(null);
    }

    @Override
    public Supplier getLastSupplierInformation() {
        return supplierRepository.getLastSupplier();
    }
}
