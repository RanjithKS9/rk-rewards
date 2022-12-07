--data for tbl_customer table
insert into tbl_customer (id, first_name, last_name, email_id, mobile_no, is_active, created_on, created_by, last_modified_on,  last_modified_by) values(1, 'James', 'Bond', 'jamesbond@gmail.com', '9999999999', 1, now(), 1, now(), 1);
insert into tbl_customer (id, first_name, last_name, email_id, mobile_no, is_active, created_on, created_by, last_modified_on,  last_modified_by) values(2, 'Indiana', 'Jones', 'alexjones@gmail.com', '9999999998', 1, now(), 1, now(), 1);
insert into tbl_customer (id, first_name, last_name, email_id, mobile_no, is_active, created_on, created_by, last_modified_on,  last_modified_by) values(3, 'Iron', 'Man', 'ironman@gmail.com', '9999999997', 1, now(), 1, now(), 1);

--data for tbl_product
insert into tbl_product (id, product_code, product_name, price, is_active, created_on, created_by, last_modified_on, last_modified_by) values(1, 'AB001', 'Shampoo', 7.20, 1, now(), 1, now(), 1);
insert into tbl_product (id, product_code, product_name, price, is_active, created_on, created_by, last_modified_on, last_modified_by) values(2, 'AB002', 'Avacado', 3.80, 1, now(), 1, now(), 1);
insert into tbl_product (id, product_code, product_name, price, is_active, created_on, created_by, last_modified_on, last_modified_by) values(3, 'AB003', 'Shoes', 13.70, 1, now(), 1, now(), 1);

--data for TBL_REWARD_POINT_CONFIG
insert into TBL_REWARD_POINT_CONFIG (id, base_amount, point) values(1, 100, 2);
insert into TBL_REWARD_POINT_CONFIG (id, base_amount, point) values(2, 50, 1);

--data for tbl_order
insert into TBL_ORDER (id, order_id, customer_id, product_id, quantity, amount, is_active, created_on, created_by, last_modified_on, last_modified_by ) values(1, 'ORD001', 1, 1, 2, 14.4, 1, '2022-08-02', 1, '2022-08-02', 1);
insert into TBL_ORDER (id, order_id, customer_id, product_id, quantity, amount, is_active, created_on, created_by, last_modified_on, last_modified_by ) values(2, 'ORD002', 1, 2, 3, 11.4, 1, '2022-08-07', 1, '2022-08-07', 1);
insert into TBL_ORDER (id, order_id, customer_id, product_id, quantity, amount, is_active, created_on, created_by, last_modified_on, last_modified_by ) values(3, 'ORD003', 1, 3, 10, 137.0, 1, '2022-09-02', 1, '2022-09-02', 1);
insert into TBL_ORDER (id, order_id, customer_id, product_id, quantity, amount, is_active, created_on, created_by, last_modified_on, last_modified_by ) values(4, 'ORD004', 1, 2, 5, 19.0, 1, '2022-09-02', 1, '2022-09-02', 1);
insert into TBL_ORDER (id, order_id, customer_id, product_id, quantity, amount, is_active, created_on, created_by, last_modified_on, last_modified_by ) values(5, 'ORD005', 1, 2, 17, 64.6, 1, '2022-10-02', 1, '2022-10-02', 1);
insert into TBL_ORDER (id, order_id, customer_id, product_id, quantity, amount, is_active, created_on, created_by, last_modified_on, last_modified_by ) values(6, 'ORD006', 1, 3, 21, 287.7, 1, '2022-10-12', 1, '2022-10-12', 1);
insert into TBL_ORDER (id, order_id, customer_id, product_id, quantity, amount, is_active, created_on, created_by, last_modified_on, last_modified_by ) values(7, 'ORD007', 1, 2, 17, 64.6, 1, '2022-11-02', 1, '2022-11-02', 1);