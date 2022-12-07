package com.demo.rewards.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbl_reward_point_config")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TblRewardPointsConfig implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double baseAmount;
    private Integer point;
}
