package com.demo.rewards.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbl_customer")
@Data
@EqualsAndHashCode(callSuper=false)
public class TblCustomer extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String firstName;
    private String lastName;
    private String emailId;
    private Long  mobileNo;

    public TblCustomer() {
    }

}
