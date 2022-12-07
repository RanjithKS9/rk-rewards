package com.demo.rewards.controller;

import com.demo.rewards.RewardsApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = RewardsApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RewardControllerIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void givenCustomerID_whenFound_thenReturnRewardPoints() throws Exception {
        int customerID = 1;

        ResponseEntity<?> responseEntity = this.restTemplate
                .getForEntity("http://localhost:" + port + "/api/v1/rewards/points/"+customerID, String.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void givenCustomerID_whenNotFound_thenReturnErrorMessage() throws Exception {

        int customerID = 10;

        ResponseEntity<?> responseEntity = this.restTemplate
                .getForEntity("http://localhost:" + port + "/api/v1/rewards/points/"+customerID, String.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
    }
}
