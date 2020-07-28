package com.aaron.daily.decimal.annotion;

import com.aaron.daily.decimal.enums.BigDecimalFormatPolicy;
import com.aaron.daily.decimal.enums.BigDecimalFormatType;
import com.aaron.daily.decimal.helper.BigDecimalDeSerializer;
import com.aaron.daily.decimal.helper.BigDecimalSerializer;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = BigDecimalSerializer.class)
@JsonDeserialize(using = BigDecimalDeSerializer.class)
public @interface BigDecimalFormat {
    String pattern() default "0.00";
    BigDecimalFormatPolicy policy() default  BigDecimalFormatPolicy.ROUND_HALF_UP;
    BigDecimalFormatType type() default BigDecimalFormatType.TO_STRING_TYPE;
}
