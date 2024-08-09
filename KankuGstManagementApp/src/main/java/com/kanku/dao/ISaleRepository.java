package com.kanku.dao;

import com.kanku.model.Purchase;
import com.kanku.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ISaleRepository extends JpaRepository<Sale,Long> {
    List<Sale> getSaleBySaleBillNo(String billNo);

    @Query(nativeQuery = true,value = "SELECT * FROM sale_info WHERE sale_date BETWEEN ? AND ?")
    List<Sale> getSalesByMonthSummary(LocalDate startDate,LocalDate endDate);
}
