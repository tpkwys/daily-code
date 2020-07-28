package com.aaron.daily.excel.listener;

import com.aaron.daily.excel.constants.ExcelHeaderConstant;
import com.aaron.daily.validator.BeanValidator;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @program: ffc-budget
 * @description: 数据简单读取监听器，中间不做任何检验处理
 * @author: tianpanke
 * @create: 2020-06-17
 **/
public class SimpleReadListener<T> extends AnalysisEventListener<T> {
    private final static Logger logger = LoggerFactory.getLogger(SimpleReadListener.class);

    private List<T> excelDataList ;

    private volatile String deptId;

    public SimpleReadListener(List<T> excelDataList,String deptId){
        this.excelDataList=excelDataList;
        this.deptId = deptId;
    }

    //校验文件格式
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        super.invokeHeadMap(headMap, context);
        if (!headMap.isEmpty()){
            ExcelHeaderConstant.excelHeader.get(deptId).forEach(l->{
                if (!headMap.containsValue(l)){
                    throw new RuntimeException("导入失败，请查验后再导入");
                }
            });
        }else {
            throw new RuntimeException("空文件");
        }
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        Boolean isBlankData=false;
        try{
            isBlankData= BeanValidator.isBlankObject(data);
        }catch (Exception e){
            logger.error("数据导入校验失败",e);
            throw new RuntimeException("数据导入校验失败");
        }
        if(!isBlankData){
            excelDataList.add(data);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
