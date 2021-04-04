package com.example.hos.dao.repository;


import com.example.hos.model.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @apiNote 权限信息
 */
public interface PermissionRepository extends JpaRepository<Permission, String> {

    Optional<List<Permission>> findAllByUidAndStatus(String uid, String status);
}
