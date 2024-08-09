package com.kanku.dao;

import com.kanku.model.Purchase;
import com.kanku.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IPurchaseRepository extends JpaRepository<Purchase,Long> {

    List<Purchase> getPurchaseByPurchaseBillNo(String billNo);

    @Query(nativeQuery = true,value = "SELECT * FROM purchase_info WHERE purchase_date BETWEEN ? AND ?")
    List<Purchase> getPurchasesByMonthSummary(LocalDate startDate, LocalDate endDate);
}
