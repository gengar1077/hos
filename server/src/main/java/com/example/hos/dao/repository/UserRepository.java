package com.example.hos.dao.repository;


import com.example.hos.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author xiping.xiong
 * @date create by 2020 09 27
 * @apiNote 员工信息
 */
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUidAndStatus(String uid, String status);

    List<User> findAllByStatus(String status);

    Optional<User> findByUsername(String username);
}
