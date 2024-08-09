package com.kanku.dao;

import com.kanku.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISupplierRepository extends JpaRepository<Supplier,Long> {

    List<Supplier> getSupplierBySupplierName(String supplierName);

    Optional<Supplier> getSupplierBySupplierContact(String contact);

    @Query(nativeQuery = true, value = "select * from supplier order by supplier_id desc limit 1")
    Supplier getLastSupplier();
}
