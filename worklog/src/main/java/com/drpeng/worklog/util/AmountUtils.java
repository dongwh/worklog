package com.drpeng.worklog.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : zhaoyp
 * @className: AmountUtils
 * @date : 16/8/13 下午2:27
 * @description: 金额转换工具
 */
public class AmountUtils {

    public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";
    public static final DecimalFormat decimalFormat = new DecimalFormat("######0.00");

    /**
     * <p>保留两位小数</p>
     *
     * @return 格式化后的浮点字符串
     * @date : 16/12/12 下午1:19
     */
    public static String floatFormat(float f) {
        BigDecimal decimal = new BigDecimal(String.valueOf(f));
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat.format(decimal.doubleValue());
    }

    /**
     * <p>保留两位小数</p>
     *
     * @return 格式化后的浮点数
     * @date : 16/12/12 下午3:14
     */
    public static float F_floatFormat(float f) {
        return Float.parseFloat(floatFormat(f));
    }

    /**
     * @title :changeF2Y
     * @author: zhaoyp
     * @date : 16/8/13 下午2:27
     * @description: 将分为单位的转换为元 （除100）
     */
    public static Object changeF2Y(String amount) throws Exception {
        if (!amount.matches(CURRENCY_FEN_REGEX)) {
            throw new Exception("分转换元失败,金额格式有误");
        }
        return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100)).floatValue();
    }

    /**
     * @className: changeY2F
     * @author : zhaoyp
     * @date : 16/8/13 下午2:28
     * @description: 将元为单位的转换为分 （乘100）
     */
    public static String changeY2F(final String amount) throws Exception {
        String fen = "";
        Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]{0,2})?");
        Matcher matcher = pattern.matcher(amount);
        if (!matcher.matches()) {
            return null;
        } else {
            NumberFormat format = NumberFormat.getInstance();
            Number number = format.parse(amount);
            double temp = number.doubleValue() * 100.0;
            // 默认情况下GroupingUsed属性为true 不设置为false时,输出结果为2,012
            format.setGroupingUsed(false);
            // 设置返回数的小数部分所允许的最大位数
            format.setMaximumFractionDigits(0);
            fen = format.format(temp);
        }
        return fen;
    }
    public static boolean isMoney(String str){
        Pattern pattern=Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
        Matcher match=pattern.matcher(str);
        if(match.matches()==false){
            return false;
        }else{
            return true;
        }
    }

    public static void main(String[] args) {
        try {
            float p = 117.5f;
            float b = 0.6f / 100;
            System.out.println(floatFormat(p * b));
        } catch (Exception e) {

        }
    }

}
