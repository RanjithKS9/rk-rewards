package com.demo.rewards.dao;

import com.demo.rewards.entity.TblRewardPointsConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRewardPointsConfigDao extends JpaRepository<TblRewardPointsConfig, Integer> {
}
