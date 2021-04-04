package com.example.hos.dao.repository;


import com.example.hos.model.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @apiNote 库存信息
 */
public interface StockRepository extends JpaRepository<Stock, String> {

    Optional<Stock> findByPidAndStatus(String uid, String status);

    List<Stock> findAllByStatus(String status);
}
