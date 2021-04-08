package com.example.hos.repository;


import com.example.hos.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @apiNote 药品信息
 */
public interface ProductRepository extends JpaRepository<Product, String> {

    Optional<Product> findByPidAndStatus(String pid, String status);

    List<Product> findAllByStatus(String status);

    Optional<Product> findByPname(String username);

    @Query("select p from Product p where p.pname like CONCAT('%',:productName,'%') and p.status = :status order by p.pid desc")
    List<Product> findLikeProductnameAndStatus(String productName, String status);
}
