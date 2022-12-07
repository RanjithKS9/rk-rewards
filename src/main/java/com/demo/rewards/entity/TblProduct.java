package com.demo.rewards.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbl_product")
@Data
@NoArgsConstructor
public class TblProduct extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String productCode;
    private String productName;
    private Double price;

    public TblProduct(Integer id) {
        this.id = id;
    }
}
