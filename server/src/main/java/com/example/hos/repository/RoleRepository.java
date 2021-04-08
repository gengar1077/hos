package com.example.hos.repository;


import com.example.hos.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @apiNote 权限信息
 */
public interface RoleRepository extends JpaRepository<Role, String> {

    Optional<Role> findRoleByRname(String rname);
}
