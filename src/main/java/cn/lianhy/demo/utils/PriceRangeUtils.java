package cn.lianhy.demo.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;

public class PriceRangeUtils {

    private final static BigDecimal BIG1 = new BigDecimal(1);
    private final static BigDecimal BIG10 = new BigDecimal(10);
    private final static BigDecimal BIG100 = new BigDecimal(100);
    private final static BigDecimal BIG1000 = new BigDecimal(1000);
    private final static BigDecimal BIG10000 = new BigDecimal(10000);
    private final static BigDecimal BIG100000 = new BigDecimal(100000);
    private final static BigDecimal NUM1 = new BigDecimal(1);
    private final static BigDecimal NUM2 = new BigDecimal(2);
    private final static BigDecimal NUM3 = new BigDecimal(3);
    private final static BigDecimal NUM4 = new BigDecimal(4);
    private final static BigDecimal NUM5 = new BigDecimal(5);

    public static LinkedHashMap<Integer, Integer> rangePrice(Integer maxPrice, Integer minPrice){
        BigDecimal bigMax = new BigDecimal(maxPrice).divide(BIG100);
        BigDecimal bigMin = new BigDecimal(minPrice).divide(BIG100);

        BigDecimal quotient_tmp1 = bigMax.subtract(bigMin).divide(NUM5);

        if(quotient_tmp1.compareTo(BIG1) <= 0 || quotient_tmp1.compareTo(BIG10) <= 0){
            LinkedHashMap<Integer, Integer> range_array= new LinkedHashMap<Integer, Integer>(){{
                put(0,1);
                put(1,2);
                put(2,3);
                put(3,4);
                put(4,5);
            }};
            return range_array;
        }else if(quotient_tmp1.compareTo(BIG10) > 0 && quotient_tmp1.compareTo(BIG100) <= 0){
            LinkedHashMap<Integer, Integer> range_array= new LinkedHashMap<Integer, Integer>(){{
                put(0,10);
                put(10,20);
                put(20,30);
                put(30,40);
                put(40,50);
            }};
            return range_array;
        }else if(quotient_tmp1.compareTo(BIG100) > 0 && quotient_tmp1.compareTo(BIG1000) <= 0){
            return packageMap(bigMax, bigMin, BIG100);
        }else if(quotient_tmp1.compareTo(BIG1000) > 0 && quotient_tmp1.compareTo(BIG10000) <= 0){
            return packageMap(bigMax, bigMin, BIG1000);
        }else{
            return packageMap(bigMax, bigMin, BIG10000);
        }
    }

    private static LinkedHashMap<Integer, Integer> packageMap(BigDecimal bigMax, BigDecimal bigMin, BigDecimal divisor){
        BigDecimal quotient_max_tmp1 = bigMax.divide(divisor, 0, RoundingMode.DOWN);
        BigDecimal quotient_min_tmp1 = bigMin.divide(divisor, 0, RoundingMode.DOWN);
        BigDecimal quotient_avg_tmp1 = quotient_max_tmp1.subtract(quotient_min_tmp1).divide(NUM5, 0, RoundingMode.DOWN).multiply(divisor);
        BigDecimal start_min = quotient_min_tmp1.multiply(divisor);
        LinkedHashMap<Integer, Integer> range_array= new LinkedHashMap<Integer, Integer>(){{
            put(start_min.multiply(NUM1).intValue(),start_min.add(quotient_avg_tmp1.multiply(NUM1)).intValue());
            put(start_min.add(quotient_avg_tmp1.multiply(NUM1)).intValue(),start_min.add(quotient_avg_tmp1.multiply(NUM2)).intValue());
            put(start_min.add(quotient_avg_tmp1.multiply(NUM2)).intValue(),start_min.add(quotient_avg_tmp1.multiply(NUM3)).intValue());
            put(start_min.add(quotient_avg_tmp1.multiply(NUM3)).intValue(),start_min.add(quotient_avg_tmp1.multiply(NUM4)).intValue());
            put(start_min.add(quotient_avg_tmp1.multiply(NUM4)).intValue(),start_min.add(quotient_avg_tmp1.multiply(NUM5)).intValue());
        }};
        return range_array;
    }
}
