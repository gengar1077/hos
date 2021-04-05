package com.example.hos.dao.repository;

import com.example.hos.model.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @Author: 吃面龙
 * @Description:
 * @Date: 2021/4/5
 */
public interface SupplierRespository extends JpaRepository<Supplier, String> {

    Optional<Supplier> findBySname(String sname);

    Optional<Supplier> findByPname(String pname);

    List<Supplier> findAllByStatus(String status);

    Optional<Supplier> findBySidAndStatus(String sid, String status);

    @Query("select p from Supplier p where p.pname like CONCAT('%',:productName,'%') and p.status = :status order by p.sid desc")
    List<Supplier> findLikeProductnameAndStatus(String productName, String status);
}
