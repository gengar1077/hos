package com.example.hos.model.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "hos_stock")
@DynamicInsert
@DynamicUpdate
public class Stock implements Serializable {

    private static final long serialVersionUID = 1491718842288446335L;

    @Id
    @GenericGenerator(name = "uuidG",strategy = "uuid2")
    @GeneratedValue(generator = "uuidG")
    @Column(name = "hos_stock_id")
    private String stockId;

    @Column(name = "hos_stock_pid")
    private String pid;

    @Column(name = "hos_stock_pname")
    private String pname;

    @Column(name = "hos_stock_p_num")
    private Integer pNum;

    @Column(name = "hos_stock_sel_num")
    private Integer selNum;

    @Column(name = "hos_stock_createtime")
    private Date createtime;

    @Column(name = "hos_stock_status")
    private String status;

}