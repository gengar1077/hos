package com.example.hos.dao.repository;


import com.example.hos.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author xiping.xiong
 * @date create by 2020 09 27
 * @apiNote 员工信息
 */
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUidAndStatus(String uid, String status);

    @Query("select u from User u where u.status = :status order by u.uid desc")
    List<User> findByStatus(String status);

    Optional<User> findByUsername(String username);
}
