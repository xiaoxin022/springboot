package com.icss.order.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Tweet implements Serializable {


    private String userName;

    private String text;
}
