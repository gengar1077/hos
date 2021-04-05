package com.example.hos.dao.repository;


import com.example.hos.model.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @apiNote 库存信息
 */
public interface StockRepository extends JpaRepository<Stock, String> {

    Optional<Stock> findByPnameAndStatus(String pname, String status);

    Optional<Stock> findByStockIdAndStatus(String sid, String status);

    Optional<Stock> findByPname(String pname);

    List<Stock> findAllByStatus(String status);

    @Query("select s from Stock s where s.pname like CONCAT('%',:productName,'%') and s.status = :status order by s.pid desc")
    List<Stock> findLikeProductnameAndStatus(String productName, String status);
}
