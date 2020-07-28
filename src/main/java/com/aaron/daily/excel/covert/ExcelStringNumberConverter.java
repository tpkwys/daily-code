package com.aaron.daily.excel.covert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.NumberUtils;
import org.apache.poi.ss.util.NumberToTextConverter;


/**
 * @author yuantongqin
 * desc:
 * 2020-06-08
 */

public class ExcelStringNumberConverter implements Converter<String> {

    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
                                    GlobalConfiguration globalConfiguration) {
        // If there are "DateTimeFormat", read as date
//        if (contentProperty != null && contentProperty.getDateTimeFormatProperty() != null) {
//            return com.alibaba.excel.util.DateUtils.format(
//                    HSSFDateUtil.getJavaDate(cellData.getNumberValue().doubleValue(),
//                            contentProperty.getDateTimeFormatProperty().getUse1904windowing(), null),
//                    contentProperty.getDateTimeFormatProperty().getFormat());
//        }
        // If there are "NumberFormat", read as number
        if (contentProperty != null && contentProperty.getNumberFormatProperty() != null) {
            return NumberUtils.format(cellData.getNumberValue(), contentProperty);
        }

        return   NumberToTextConverter.toText(cellData.getNumberValue().doubleValue());
        // return NumberUtils.format(cellData.getDoubleValue(), contentProperty);
    }

    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty contentProperty,
                                       GlobalConfiguration globalConfiguration) {
        return new CellData(Double.valueOf(value));
    }
}
