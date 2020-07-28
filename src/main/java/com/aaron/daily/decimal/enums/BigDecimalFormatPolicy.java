package com.aaron.daily.decimal.enums;

/**
 * @desc: BigDecimal格式化策略
 * @author: tianpanke
 * @create: 2019-12-12 10:20
 **/
public enum BigDecimalFormatPolicy {
   ROUND_HALF_UP(1,"四舍五入"),
   ROUND_CUT_TAIL(2,"截尾");
    private Integer value;
    private String text;
    BigDecimalFormatPolicy(Integer value,String text){
        this.value=value;
        this.text=text;
    }

    public Integer getValue() {
        return value;
    }}
