package com.kanku.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "supplier_info")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;

    private String supplierName;

    private String supplierContact;

    @Column(columnDefinition = "LONGTEXT")
    private String supplierAddress;

    @Column(unique = true)
    private String supplierGSTNo;
}
