package com.icss.order.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderDemo implements Serializable {

    /**
     * 订单ID
     */
    private long orderID;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

}
