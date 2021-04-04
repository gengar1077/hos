package com.example.hos.model.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "hos_permission")
@DynamicInsert
@DynamicUpdate
public class Permission implements Serializable {

    private static final long serialVersionUID = 1496310963718052884L;

    @Id
    @GenericGenerator(name = "uuidG",strategy = "uuid2")
    @GeneratedValue(generator = "uuidG")
    @Column(name = "hos_per_id")
    private String perId;

    @Column(name = "hos_per_rid")
    private String rid;

    @Column(name = "hos_per_r_name")
    private String rname;

    @Column(name = "hos_per_uid")
    private String uid;

    @Column(name = "hos_per_username")
    private String username;

    @Column(name = "hos_per_status")
    private String status;

}