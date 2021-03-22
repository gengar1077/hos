-- 1、用户表：
CREATE SEQUENCE SEQ_user INCREMENT BY 1 START WITH 1 MAXvalue 99999999;
create table t_user(
    u_id varchar2(11) primary key,
    username varchar2(20),
    password varchar2(50),
    phone varchar2(11),
    role_id varchar2(11),
    photo varchar2(128),
    remark varchar2(128),
    wei varchar2(128),
    status char(1)
);
comment on table t_user is '用户表';

-- 2、角色表：
CREATE SEQUENCE SEQ_role INCREMENT BY 1 START WITH 1 MAXvalue 99999999;
create table t_role(
    r_id varchar2(11) primary key,
    r_name varchar2(30),
    remark varchar2(20),
    status char(1)
);
comment on table t_role is '角色表';

-- 3、权限表：
CREATE SEQUENCE SEQ_permission INCREMENT BY 1 START WITH 1 MAXvalue 99999999;
create table t_permission(
    per_id varchar2(11) primary key,
    role_id varchar2(11),
    menu_id varchar2(11)
);

-- 4、药品表
CREATE SEQUENCE SEQ_product INCREMENT BY 1 START WITH 1 MAXvalue 99999999;
create table t_product(
    p_id varchar2(11) primary key,
    s_id varchar2(11),
    p_name varchar2(128),
    place varchar2(128),
    spec varchar2(128),
    price number(8),
    remark varchar2(128),
    status char(1)
);

-- 5、供应商表
CREATE SEQUENCE SEQ_supplier INCREMENT BY 1 START WITH 1 MAXvalue 99999999;
create table t_supplier(
    s_id varchar2(11) primary key,
    p_name varchar2(128),
    tel varchar2(32),
    address varchar2(128),
    status char(1)
);

-- 6、库存表
CREATE SEQUENCE SEQ_stock INCREMENT BY 1 START WITH 1 MAXvalue 99999999;
create table t_stock(
    stock_id varchar2(11) primary key,
    p_id varchar2(11),
    p_name varchar2(128),
    p_num number(8),
    sel_num number(8),
    createTime date,
    status char(1)
);

-- 7、销售表
CREATE SEQUENCE SEQ_sell INCREMENT BY 1 START WITH 1 MAXvalue 99999999;
create table t_sell(
    sell_id varchar2(11) primary key,
    money number(11),
    p_num number(8),
    pay_type number(2),
    status number(2),
    operator varchar2(32),
    remark varchar2(128),
    createTime date,
    status char(1)
);

-- 8、进货表
CREATE SEQUENCE SEQ_order INCREMENT BY 1 START WITH 1 MAXvalue 99999999;
create table t_order(
    o_id varchar2(11) primary key,
    money number(11),
    p_num number(8),
    pay_type number(2),
    status number(2),
    operator varchar2(32),
    remark varchar2(128),
    createTime date,
    status char(1)
);