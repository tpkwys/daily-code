package com.aaron.daily.excel.listener;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.*;

import java.util.List;

/**
 * @program: ffc-reconciliation
 * @description: 匹配失败数据行红色渲染
 * @author: tianpanke
 * @create: 2020-05-29
 **/
public class PaintRedAndLeftAlignListener implements CellWriteHandler {
    private List<Integer> dataFlagList=null;

    public PaintRedAndLeftAlignListener(List<Integer> dataFlagList){
        this.dataFlagList=dataFlagList;
    }
    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {

    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {

    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        Workbook workbook=writeSheetHolder.getSheet().getWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();

        int rowIndex=cell.getRowIndex();
        if(rowIndex<=dataFlagList.size()){
            int columnIndex=cell.getColumnIndex();
            int  dataFlag=dataFlagList.get(rowIndex-1);
            int mask=1<<columnIndex;//掩码
            int curNum=dataFlag&mask;

            //表示二进制序列 当前字段对应位置 为0  字体红色渲染
            if(mask!=curNum){
//                Font font = workbook.createFont();
//                font.setColor(IndexedColors.RED.getIndex());
//                //设置字体颜色
//                cellStyle.setFont(font);
//                cellStyle.setFillBackgroundColor(IndexedColors.RED.getIndex());
                cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            }
            //设置对齐方式
            cellStyle.setAlignment(HorizontalAlignment.LEFT);
            cell.setCellStyle(cellStyle);
        }
    }
}
