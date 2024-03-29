package com.icss.order.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * 数字格式化工具类
 */
public class NumberFormatUtil {

    /**
     * 格式化工具集合
     */
    private static final Map<String, NumberFormat> fmtMap = new HashMap<String, NumberFormat>();

    private static final String EX = "0.00";

    /**
     * @param ex 格式表达式
     * @return 数字格式化工具
     */
    private static NumberFormat getFormat(String ex) {
        NumberFormat fmt = fmtMap.get(ex);
        if (fmt == null) {
            fmt = new DecimalFormat(ex);
            fmtMap.put(ex, fmt);
        }
        return fmt;
    }

    /**
     * 格式化
     *
     * @param obj 对象
     * @param ex 格式表达式
     * @return 数字字符串
     */
    public static String format(Object obj, String ex) {
        return getFormat(ex).format(obj);
    }
    /**
     * 格式化 金额对象
     *
     * @param obj 对象
     * @return 数字字符串
     */
    public static String format(Object obj) {
        if (obj == null) {
            obj = new BigDecimal(0);
        }
        return getFormat(EX).format(obj);
    }
    /**
     * 格式化
     *
     * @param number 数字
     * @param ex 格式表达式
     * @return 数字字符串
     */
    public static String format(double number, String ex) {
        return getFormat(ex).format(number);
    }

    /**
     * 格式化
     *
     * @param number 数字
     * @param ex 格式表达式
     * @return 数字字符串
     */
    public static String format(long number, String ex) {
        return getFormat(ex).format(number);
    }

    /**
     * 解析
     *
     * @param source 数字字符串
     * @param ex 格式表达式
     * @return 数字
     */
    public static Number parse(String source, String ex) throws ParseException {
        return getFormat(ex).parse(source);
    }

}
