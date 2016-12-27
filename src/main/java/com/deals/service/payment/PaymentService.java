package com.deals.service.payment;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.ui.Model;

import java.util.Map;

/**
 * Created by gsitgithub on 20/11/2016.
 */
public interface PaymentService {

    enum PaymentType {BUY_TOPUP, BUY_PLAN}

    Map<String, Object> initPayment(Long userId, Long planId, PaymentType type, double amount);

    void processPayment(Map<String, String> params);
}
