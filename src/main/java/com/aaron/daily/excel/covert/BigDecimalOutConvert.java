package com.aaron.daily.excel.covert;

import com.aaron.daily.decimal.enums.BigDecimalFormatPolicy;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @program: ffc-reconciliation
 * @description: 金额类型字段导出转换器
 * @author: tianpanke
 * @create: 2020-06-12
 **/
public class BigDecimalOutConvert implements Converter<BigDecimal> {

    private String  pattern="0.00"; //格式化 模式
    private BigDecimalFormatPolicy formatPolicy=BigDecimalFormatPolicy.ROUND_HALF_UP;//默认 四舍五入

    public BigDecimalOutConvert(){
    }
    public BigDecimalOutConvert(String pattern, BigDecimalFormatPolicy formatPolicy){
        this.pattern=pattern;
        this.formatPolicy=formatPolicy;
    }
    @Override
    public Class supportJavaTypeKey() {
        return BigDecimal.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public BigDecimal convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return null;
    }

    @Override
    public CellData convertToExcelData(BigDecimal value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        //数据格式化
        RoundingMode roundingMode=(formatPolicy==BigDecimalFormatPolicy.ROUND_HALF_UP)?RoundingMode.HALF_UP:RoundingMode.DOWN;
        DecimalFormat decimalFormat=new DecimalFormat(pattern);
        decimalFormat.setRoundingMode(roundingMode);
        String formatResult=decimalFormat.format(value);
        return new CellData<>(formatResult);
    }
}
