package com.aaron.daily.decimal.enums;

public enum BigDecimalFormatType {
    TO_NUMBER_TYPE(1,"转Number类型"),
    TO_STRING_TYPE(2,"转String类型")
    ;
    private Integer value;
    private String text;
    BigDecimalFormatType(Integer value,String text){
        this.value=value;
        this.text=text;
    }
    public Integer getValue() {
        return value;
    }
    public String getText() {
        return text;
    }

}
