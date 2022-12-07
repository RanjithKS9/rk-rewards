package com.demo.rewards.service;

import com.demo.rewards.dao.ICustomerDao;
import com.demo.rewards.dao.IOrderDao;
import com.demo.rewards.dao.IRewardPointsConfigDao;
import com.demo.rewards.dto.RewardDto;
import com.demo.rewards.entity.TblCustomer;
import com.demo.rewards.entity.TblOrder;
import com.demo.rewards.entity.TblRewardPointsConfig;
import com.demo.rewards.exception.RewardsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static com.demo.rewards.constants.RewardConstants.*;
import static com.demo.rewards.constants.RewardErrorConstants.INVALID_CUSTOMER_ID;
import static com.demo.rewards.constants.RewardErrorConstants.NO_TRANSACTION_AVAILABLE;
import static com.demo.rewards.constants.RewardErrorConstants.NUMBER_CUSTOMER_ID;

@Service
@Slf4j
public class RewardService {
	@Autowired
	ICustomerDao iCustomerDao;
	@Autowired
	IOrderDao iOrderDao;
	@Autowired
	IRewardPointsConfigDao iRewardPointsConfigDao;
	@Value("${back.month}")
	Integer backMonth;
	private static final DateFormat df = new SimpleDateFormat(YYYY_MM);
	private static final DecimalFormat df1 = new DecimalFormat(DECIMAL_FRACTION);

	public RewardDto findRewardPoints(Integer customerId) throws RewardsException {
		log.info("start findRewardPoints - {}", customerId);
		String rewardPoints;
		if(customerId < 0){
			throw new RewardsException(NUMBER_CUSTOMER_ID);
		}
		Optional<TblCustomer> customerOptional = iCustomerDao.findById(customerId);
		if (customerOptional.isEmpty())
			throw new RewardsException(INVALID_CUSTOMER_ID);

		TblCustomer tblCustomer = new TblCustomer();
		tblCustomer.setId(customerId);
		Date fromDate = findBackDate();
		List<TblOrder> tblOrderList = iOrderDao.findAllByCustomerIdAndCreatedOnGreaterThanEqual(tblCustomer, fromDate);

		if (tblOrderList.isEmpty())
			throw new RewardsException(NO_TRANSACTION_AVAILABLE);

		Map<String, List<TblOrder>> dateAmountMap = tblOrderList.stream().collect(
				Collectors.groupingBy(data -> df.format(data.getCreatedOn()), Collectors.toCollection(ArrayList::new)));

		rewardPoints = df1.format(calculateRewards(dateAmountMap));
		log.info("end findRewardPoints - reward points - {} for customer id - {}", rewardPoints, customerId);
		return RewardDto.builder().rewardPoints(rewardPoints).build();
	}

	private Date findBackDate() {
		LocalDateTime localDateTime = LocalDateTime.now();
		localDateTime = localDateTime.minusMonths(backMonth);
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	private Double calculateRewards(Map<String, List<TblOrder>> dateAmountMap) throws RewardsException {
		double rewardPoints = 0.0;
		int pointsFor100;
		int pointsFor50;
		List<TblRewardPointsConfig> rewardPointsConfigs = iRewardPointsConfigDao.findAll();
		if (rewardPointsConfigs.isEmpty()) {
			log.error("calculateRewards - could not find rewardPointsConfig");
			throw new RewardsException("Could not find rewards points settings!");
		}
		Optional<Integer> optionalPointsFor100 = rewardPointsConfigs.stream()
				.filter(data -> data.getBaseAmount() == AMOUNT_100).map(TblRewardPointsConfig::getPoint).findAny();
		Optional<Integer> optionalPointsFor50 = rewardPointsConfigs.stream()
				.filter(data -> data.getBaseAmount() == AMOUNT_50).map(TblRewardPointsConfig::getPoint).findAny();

		if (optionalPointsFor100.isEmpty()) {
			log.error("calculateRewards - could not find rewardPoints for $100");
			throw new RewardsException("Could not find rewards points settings!");
		}
		pointsFor100 = optionalPointsFor100.get();

		if (optionalPointsFor50.isEmpty()) {
			log.error("calculateRewards - could not find rewardPoints for $50");
			throw new RewardsException("Could not find rewards points settings!");
		}
		pointsFor50 = optionalPointsFor50.get();
		for (Map.Entry<String, List<TblOrder>> entry : dateAmountMap.entrySet()) {
			List<TblOrder> orders = entry.getValue();
			Optional<Double> optionalTotalAmount = orders.stream().map(TblOrder::getAmount).reduce(Double::sum);
			if (optionalTotalAmount.isPresent()) {
				double amount100 = optionalTotalAmount.get() - AMOUNT_100;
				amount100 = Math.max(0, amount100);
				rewardPoints += amount100 * pointsFor100; 

				double amount50 = optionalTotalAmount.get() - amount100 - AMOUNT_50;
				amount50 = Math.max(0, amount50);
				rewardPoints += amount50 * pointsFor50;
			}
		}

		return rewardPoints;
	}

}
