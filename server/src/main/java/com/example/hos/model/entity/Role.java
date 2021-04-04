package com.example.hos.model.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "hos_role")
@DynamicInsert
@DynamicUpdate
public class Role implements Serializable {

    private static final long serialVersionUID = -5784519806531999493L;

    @Id
    @GenericGenerator(name = "uuidG",strategy = "uuid2")
    @GeneratedValue(generator = "uuidG")
    @Column(name = "hos_r_id")
    private String rid;

    @Column(name = "hos_r_name")
    private String rname;

    @Column(name = "hos_r_remark")
    private String remark;

    @Column(name = "hos_r_status")
    private String status;

}