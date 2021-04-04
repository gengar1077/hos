package com.example.hos.model.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "hos_user")
@DynamicInsert
@DynamicUpdate
public class User implements Serializable {

    private static final long serialVersionUID = 8921982022393967610L;

    @Id
    @GenericGenerator(name = "uuidG",strategy = "uuid2")
    @GeneratedValue(generator = "uuidG")
    @Column(name = "hos_user_id")
    private String uid;

    /**
     * @desc 用户姓名
     */
    @Column(name = "hos_user_name")
    private String username;

    @Column(name = "hos_password")
    private String password;

    @Column(name = "hos_phone")
    private String phone;

    @Column(name = "hos_role_Id")
    private String roleId;

    @Column(name = "hos_photo")
    private String photo;

    @Column(name = "hos_remark")
    private String remark;

    @Column(name = "hos_wei")
    private String wei;

    @Column(name = "hos_status")
    private String status;
}