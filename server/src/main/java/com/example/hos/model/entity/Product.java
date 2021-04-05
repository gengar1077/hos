package com.example.hos.model.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@Table(name = "hos_product")
@DynamicInsert
@DynamicUpdate
public class Product implements Serializable {

    private static final long serialVersionUID = -3986074941445853098L;

    @Id
    @GenericGenerator(name = "uuidG",strategy = "uuid2")
    @GeneratedValue(generator = "uuidG")
    @Column(name = "hos_p_id")
    private String pid;

    /**
     * @desc 供应商姓名
     */
    @Column(name = "hos_s_name")
    private String sid;

    /**
     * @desc 药品名称
     */
    @Column(name = "hos_p_name")
    private String pname;

    @Column(name = "hos_p_place")
    private String place;

    @Column(name = "hos_p_spec")
    private String spec;

    @Column(name = "hos_p_price")
    private Integer price;

    @Column(name = "hos_p_remark")
    private String remark;

    @Column(name = "hos_p_status")
    private String status;

}