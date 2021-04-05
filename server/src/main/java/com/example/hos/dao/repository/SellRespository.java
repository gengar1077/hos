package com.example.hos.dao.repository;

import com.example.hos.model.entity.Sell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @Author: 吃面龙
 * @Description:
 * @Date: 2021/4/5
 */
public interface SellRespository extends JpaRepository<Sell, String> {

    List<Sell> findAllByStatus(String status);

    Optional<Sell> findBySellIdAndStatus(String sellId, String status);

    @Query("select p from Sell p where p.pname like CONCAT('%',:productName,'%') and p.status = :status order by p.sellId desc")
    List<Sell> findLikeProductnameAndStatus(String productName, String status);
}
