package com.demo.rewards.dao;

import com.demo.rewards.entity.TblCustomer;
import com.demo.rewards.entity.TblOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IOrderDao extends JpaRepository<TblOrder, Integer> {
    List<TblOrder> findAllByCustomerIdAndCreatedOnGreaterThanEqual(TblCustomer tblCustomer, Date createdOn);
    List<TblOrder> findAllByCustomerId(TblCustomer tblCustomer);
}
