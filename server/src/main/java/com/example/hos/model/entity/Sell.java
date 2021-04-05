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
@Table(name = "hos_sell")
@DynamicInsert
@DynamicUpdate
public class Sell implements Serializable {

    private static final long serialVersionUID = -901541762579003497L;

    @Id
    @GenericGenerator(name = "uuidG",strategy = "uuid2")
    @GeneratedValue(generator = "uuidG")
    @Column(name = "hos_sell_id")
    private String sellId;

    @Column(name = "hos_sell_money")
    private Long money;

    @Column(name = "hos_sell_p_name")
    private String pname;

    @Column(name = "hos_sell_p_num")
    private Integer pNum;

    @Column(name = "hos_sell_operator")
    private String operator;

    @Column(name = "hos_sell_remark")
    private String remark;

    @Column(name = "hos_sell_createtime")
    private Date createtime;

    @Column(name = "hos_sell_status")
    private String status;

}