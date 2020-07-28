package com.aaron.daily.decimal.helper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @descri: Bigdecimal反序列化
 * @author: tianpanke
 * @create: 2019-12-12 10:10
 **/
public class BigDecimalDeSerializer extends JsonDeserializer<BigDecimal> {
    @Override
    public BigDecimal deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        if(jsonParser != null && jsonParser.getText()!=null&&!"".equals(jsonParser.getText())){
            return new BigDecimal(jsonParser.getText());
        }else{
            return null;
        }
    }
}
