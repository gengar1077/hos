package com.example.hos.dao.repository;


import com.example.hos.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @apiNote 药品信息
 */
public interface ProductRepository extends JpaRepository<Product, String> {

    Optional<Product> findByPidAndStatus(String pid, String status);

    List<Product> findAllByStatus(String status);

    Optional<Product> findByPname(String username);
}
