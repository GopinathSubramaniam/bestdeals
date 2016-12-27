package com.deals.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by gsitgithub on 19/12/2016.
 */
@Getter
@Setter
public class PaymentVO {
    private Long id;
    private double amount;
    private String txnid;
    private String udf2;
    private String serviceProvider;
    private Long planId;
    private String plan;
    private String phone;
    private String surl;
    private String furl;
    private String udf1;
    private String udf3;
    private String udf4;


}
