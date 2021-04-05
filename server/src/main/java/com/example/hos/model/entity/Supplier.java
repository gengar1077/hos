package com.example.hos.model.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "hos_supplier")
@DynamicInsert
@DynamicUpdate
public class Supplier implements Serializable {

    private static final long serialVersionUID = 2264965277600128926L;

    @Id
    @GenericGenerator(name = "uuidG",strategy = "uuid2")
    @GeneratedValue(generator = "uuidG")
    @Column(name = "hos_s_id")
    private String sid;

    @Column(name = "hos_s_sname")
    private String sname;

    @Column(name = "hos_s_pname")
    private String pname;

    @Column(name = "hos_s_tel")
    private String tel;

    @Column(name = "hos_s_address")
    private String address;

    @Column(name = "hos_s_status")
    private String status;

}