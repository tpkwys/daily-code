package com.aaron.daily.excel.listener;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.*;

import java.util.List;

/**
 * @program: ffc-budget
 * @description: 对齐方式监听器
 * @author: tianpanke
 * @create: 2020-06-28
 **/
public class AlignmentWriteListener implements CellWriteHandler {

    private HorizontalAlignment horizontalAlignment;

    public AlignmentWriteListener(HorizontalAlignment horizontalAlignment){
        this.horizontalAlignment=horizontalAlignment;
    }
    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        Workbook workbook=writeSheetHolder.getSheet().getWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        //设置对齐方式
        cellStyle.setAlignment(horizontalAlignment==null? HorizontalAlignment.LEFT:horizontalAlignment);
        cell.setCellStyle(cellStyle);
    }
    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {

    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {

    }
}
