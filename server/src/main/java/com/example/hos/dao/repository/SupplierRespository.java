package com.example.hos.dao.repository;

import com.example.hos.model.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @Author: 吃面龙
 * @Description:
 * @Date: 2021/4/5
 */
public interface SupplierRespository extends JpaRepository<Supplier, String> {

    Optional<Supplier> findBySname(String sname);

    List<Supplier> findAllByStatus(String status);

    Optional<Supplier> findBySidAndStatus(String sid, String status);
}
