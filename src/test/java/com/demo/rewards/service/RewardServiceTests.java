package com.demo.rewards.service;

import com.demo.rewards.dao.ICustomerDao;
import com.demo.rewards.dao.IOrderDao;
import com.demo.rewards.dao.IRewardPointsConfigDao;
import com.demo.rewards.dto.RewardDto;
import com.demo.rewards.entity.TblCustomer;
import com.demo.rewards.entity.TblOrder;
import com.demo.rewards.entity.TblProduct;
import com.demo.rewards.entity.TblRewardPointsConfig;
import com.demo.rewards.exception.RewardsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RewardServiceTests {

    @InjectMocks
    RewardService rewardService;
    @Mock
    ICustomerDao iCustomerDao;
    @Mock
    IOrderDao iOrderDao;
    @Mock
    IRewardPointsConfigDao iRewardPointsConfigDao;
    Integer validCustomerID = 1;
    Integer invalidCustomerID = 10;

    Optional<TblCustomer> optionalTblCustomer;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(rewardService, "backMonth", 3);
        TblCustomer tblCustomer = new TblCustomer();
        tblCustomer.setFirstName("James");
        tblCustomer.setLastName("Bond");
        tblCustomer.setEmailId("Jamesbond@gmail.com");
        tblCustomer.setId(validCustomerID);
        optionalTblCustomer = Optional.of(tblCustomer);

    }


    @Test
    public void givenCustomerID_whenFound_thenReturnRewardPoints() {
        List<TblOrder> tblOrderList = new ArrayList<>();
        tblOrderList.add(getOrder(1, "ORD001", 1, 14.4, 2, "2022-08-02"));
        tblOrderList.add(getOrder(2, "ORD002", 2, 11.4, 3, "2022-08-07"));
        tblOrderList.add(getOrder(3, "ORD003", 3, 137.0, 10, "2022-09-02"));
        tblOrderList.add(getOrder(4, "ORD004", 2, 19.0, 5, "2022-09-02"));
        tblOrderList.add(getOrder(5, "ORD005", 2, 64.6, 17, "2022-10-02"));
        tblOrderList.add(getOrder(6, "ORD006", 3, 287.7, 21, "2022-10-12"));
        tblOrderList.add(getOrder(7, "ORD007", 2, 64.6, 17, "2022-11-02"));

        List<TblRewardPointsConfig> tblRewardPointsConfigList = new ArrayList<>();
        tblRewardPointsConfigList.add(new TblRewardPointsConfig(1, 100.0, 2));
        tblRewardPointsConfigList.add(new TblRewardPointsConfig(2, 50.0, 1));
        TblCustomer tblCustomer = new TblCustomer();
        tblCustomer.setId(validCustomerID);
        lenient().when(iCustomerDao.findById(validCustomerID)).thenReturn(optionalTblCustomer);
        when(iOrderDao.findAllByCustomerIdAndCreatedOnGreaterThanEqual(any(), any())).thenReturn(tblOrderList);
        when(iRewardPointsConfigDao.findAll()).thenReturn(tblRewardPointsConfigList);
        RewardDto rewardDto = rewardService.findRewardPoints(validCustomerID);
        assertNotNull(rewardDto);
        assertEquals(rewardDto.getRewardPoints(), "731.20");
    }

    @Test
    public void givenCustomerID_whenNotFound_thenReturnErrorMessage() {
        RewardsException thrown = assertThrows(RewardsException.class,
                () -> rewardService.findRewardPoints(invalidCustomerID), "Please provide a valid customer ID!");
        assertTrue(thrown.getErrorMessage().contains("Please provide a valid customer ID!"));
    }

    private Date getDate(String dateStr) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    private TblOrder getOrder(int id, String orderId, int productID, double amount, int quantity, String dateStr) {
        TblOrder tblOrder = new TblOrder(id, orderId, optionalTblCustomer.get(), new TblProduct(productID), amount, quantity);
        tblOrder.setCreatedOn(getDate(dateStr));
        tblOrder.setCreatedBy(1);
        tblOrder.setLastModifiedOn(getDate(dateStr));
        tblOrder.setLastModifiedBy(1);
        tblOrder.setIsActive(true);

        return tblOrder;
    }
}