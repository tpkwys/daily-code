package com.aaron.daily.decimal.helper;

import com.aaron.daily.decimal.annotion.BigDecimalFormat;
import com.aaron.daily.decimal.enums.BigDecimalFormatPolicy;
import com.aaron.daily.decimal.enums.BigDecimalFormatType;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;

/**
 * @descri: BigDecimal序列化
 * @author: tianpanke
 * @create: 2019-12-12 10:08
 **/
public class BigDecimalSerializer extends JsonSerializer<BigDecimal> implements ContextualSerializer {
    private String  pattern; //格式化 模式
    private BigDecimalFormatPolicy formatPolicy;//默认 截尾
    private BigDecimalFormatType toType;
    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if(value!=null){
            RoundingMode roundingMode=(formatPolicy==BigDecimalFormatPolicy.ROUND_HALF_UP)?RoundingMode.HALF_UP:RoundingMode.DOWN;
            DecimalFormat decimalFormat=new DecimalFormat(pattern);
            decimalFormat.setRoundingMode(roundingMode);
            String formatResult=decimalFormat.format(value);
            if(toType==BigDecimalFormatType.TO_NUMBER_TYPE) gen.writeNumber(formatResult); else gen.writeString(formatResult);
        }else{
            gen.writeNumber(value);
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        if(beanProperty !=null ){
            if(Objects.equals(beanProperty.getType().getRawClass(),BigDecimal.class)){
                BigDecimalFormat bigDecimalFormat = beanProperty.getAnnotation((BigDecimalFormat.class));
                if(bigDecimalFormat == null){
                    bigDecimalFormat = beanProperty.getContextAnnotation(BigDecimalFormat.class);
                }
                BigDecimalSerializer bigDecimalSerializer = new BigDecimalSerializer();
                if(bigDecimalFormat != null){
                    bigDecimalSerializer.pattern = bigDecimalFormat.pattern();
                    bigDecimalSerializer.formatPolicy= bigDecimalFormat.policy();
                    bigDecimalSerializer.toType= bigDecimalFormat.type();
                }
                return bigDecimalSerializer;
            }
            return serializerProvider.findValueSerializer(beanProperty.getType(),beanProperty);
        }
        return serializerProvider.findNullValueSerializer(beanProperty);
    }
}
