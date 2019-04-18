package com.icss.order.util;

import java.math.BigDecimal;

/**
 * Class Description : 该类是把阿拉伯数字转换成中文大写的类。
 *
 *
 *
 * 汉字大写金额数字，一律用正楷字或行书字书写，如壹、贰、叁、肆、伍、陆、柒、捌、玖、拾、佰、仟、万、亿、圆（元）、
 * 角、分、零、整（正）等易于辨认、不易涂改的字样。
 * 不得用一、二（两）、三、四、五、六、七、八、九、十、念、毛、另（或０）等字样代替，不得任意自造简化字。
 *
 * 大写金额数字到元或角为止的，在“元”或“角”字之后应写“整”或“正”字；大写金额数字有分的，分字后面不写“整”字。
 * 大写金额数字前未印有人民币字样的，应加填“人民币”三字，“人民币”三字与金额数字之间不得留有空白。
 *
 * 阿拉伯金额数字中间有“０”时，汉字大写金额要写“零”字，如＄１０１．５０，汉字大写金额应写成人民币壹佰零壹圆伍角整。
 * 阿拉伯金额数字中间连续有几个“０”时，汉字大写金额中可以只写一个“零”字，如￥１，００４．５６，汉字大写金额应写成
 * 人民币壹仟零肆圆伍角陆分。阿拉伯金额数字元位是“０”，或数字中间连续有几个“０”，元位也是“０”，但角位不是“０”时，
 * 汉字大写金额可只写一个“零”字，也可不写“零”字，如＄１，３２０．５６，汉字大写金额应写成人民币壹仟叁佰贰拾圆零伍
 * 角陆分，或人民币壹仟叁佰贰拾圆伍角陆分。又如＄１，０００．５６，汉字大写金额应写成人民币壹仟圆零伍角陆分，或人民
 * 币壹仟圆伍角陆分。
 *
 *                                                      摘自《会计人员工作规则》（８４）财会１６号
 *
 *
 *
 *
 */
public class NumberToStringForChineseMoney {

    static String HanDigiStr[] = new String[] { "零", "壹", "贰", "叁", "肆", "伍", "陆",
            "柒", "捌", "玖" };

    static String HanDiviStr[] = new String[] { "", "拾", "佰", "仟", "万", "拾", "佰", "仟",
            "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万",
            "拾", "佰", "仟" };

    /**
     * 负责把小数点前面的数转换为大写中文
     *
     * 输入字符串必须正整数，只允许前面有空格(必须右对齐)，不允许前面有零
     *
     * @param numberStr
     * @return
     */
    private static String positiveIntegerToHanString(String numberStr) {
        String RMBStr = "";
        boolean lastzero = false;
        boolean hasvalue = false; // 亿、万进位前有数值标记
        int len, n;
        len = numberStr.length();
        if (len > 15)
            return "金额过大!";
        for (int i = len - 1; i >= 0; i--) {
            if (numberStr.charAt(len - i - 1) == ' ')
                continue;
            n = numberStr.charAt(len - i - 1) - '0';
            if (n < 0 || n > 9)
                return "金额含非数字字符!";

            if (n != 0) {
                if (lastzero)
                    RMBStr += HanDigiStr[0]; // 若干零后若跟非零值，只显示一个零
                RMBStr += HanDigiStr[n];
                RMBStr += HanDiviStr[i]; // 非零值后加进位，个位为空
                hasvalue = true; // 置万进位前有值标记

            } else {
                if ((i % 8) == 0 || ((i % 8) == 4 && hasvalue)) // 亿万之间必须有非零值方显示万
                    RMBStr += HanDiviStr[i]; // “亿”或“万”
            }
            if (i % 8 == 0)
                hasvalue = false; // 万进位前有值标记逢亿复位
            lastzero = (n == 0) && (i % 4 != 0);
        }

        if (RMBStr.length() == 0)
            return HanDigiStr[0]; // 输入空字符或"0"，返回"零"
        return RMBStr;
    }

    /**
     * 输入double型数转换为大写中文
     *
     * @param doubleValue
     * @return 大写中文
     */
    public static String getChineseMoneyStringForDoubleVal(double doubleValue) {
        String SignStr = "";
        String TailStr = "";
        long fraction, integer;
        int jiao, fen;

        if (doubleValue < 0) {
            doubleValue = -doubleValue;
            SignStr = "负";
        }
        if (doubleValue > 99999999999999.999
                || doubleValue < -99999999999999.999)
            return "金额数值位数过大!";
        // 四舍五入到分
        long temp = Math.round(doubleValue * 100);
        integer = temp / 100;
        fraction = temp % 100;
        jiao = (int) fraction / 10;
        fen = (int) fraction % 10;
        if (jiao == 0 && fen == 0) {
            TailStr = "整";
        } else {
            TailStr = HanDigiStr[jiao];
            if (jiao != 0)
                TailStr += "角";
            if (integer == 0 && jiao == 0) // 零圆后不写零几分
                TailStr = "";
            if (fen != 0)
                TailStr += HanDigiStr[fen] + "分";
        }

        return (doubleValue>=1)?(SignStr + positiveIntegerToHanString(String.valueOf(integer))
                + "圆" + TailStr):TailStr;
    }

    /**
     * 输入BigDecimal型数转换为大写中文
     *
     * 精度取决于BigDecimal 的 public double doubleValue() 方法： 是基本收缩转换。 如果此 BigDecimal
     * 的数量太大而不能表示为 double，则将其适当地转换为 Double.NEGATIVE_INFINITY 或
     * Double.POSITIVE_INFINITY。 即使在返回值为有限值的情况下，此转换也可能丢失关于 BigDecimal 值精度的信息。
     *
     * @param bigDecimalVal
     * @return 大写中文
     */
    public static String getChineseMoneyStringForBigDecimal(BigDecimal bigDecimalVal) {
        return getChineseMoneyStringForDoubleVal(bigDecimalVal
                .doubleValue());
    }
}