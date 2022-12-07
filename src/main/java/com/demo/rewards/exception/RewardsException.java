package com.demo.rewards.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class RewardsException extends RuntimeException{
    private String errorMessage;

}
