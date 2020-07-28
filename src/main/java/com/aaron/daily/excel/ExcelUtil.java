package com.aaron.daily.excel;

import com.aaron.daily.excel.covert.ExcelStringNumberConverter;
import com.aaron.daily.excel.listener.SimpleReadListener;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: ffc-reconciliation
 * @description:
 * @author: tianpanke
 * @create: 2020-05-29
 **/
public class ExcelUtil {

    public static <T>  List<T> simpleReadExcel(MultipartFile multipartFile, Class c, String deptId) throws Exception{
        if(multipartFile==null||multipartFile.getInputStream()==null){
            throw new RuntimeException("导入失败，获取文件输入流异常");
        }
        List<T> dataList=new ArrayList<>();
        String name=multipartFile.getOriginalFilename();
        InputStream inputStream=multipartFile.getInputStream();
        EasyExcel.read(inputStream,c,new SimpleReadListener(dataList,deptId)).registerConverter(new ExcelStringNumberConverter()).sheet(0).doRead();
        return dataList;
    }

    /**
     *
     * @param response http响应对象
     * @param abstractInfo 摘要信息
     * @param dataList 核心数据列表
     * @param fileName 文件名称
     * @param writeHandler 监听器
     * @param modelName 模板地址 (该模板统一约定放到 resource/template/文件夹下，否则后果自负)
     * @param  needHead 需要头否（如果模板里已经有了，此处就传false）
     */
    public static <T,D> void
    writeExcel(HttpServletResponse response, D abstractInfo, List<T> dataList
            , String fileName, WriteHandler writeHandler, String modelName, Boolean needHead, List<Converter> converters) throws Exception{
        OutputStream outputStream=getOutputStream(fileName, response);
        ExcelWriterBuilder excelWriterBuilder= EasyExcel.write(outputStream);
        //映射关系
        if(!CollectionUtils.isEmpty(dataList)){
            excelWriterBuilder.head(dataList.get(0).getClass());
        }
        //是否需要头
        if(needHead!=null){
            excelWriterBuilder.needHead(needHead);
        }
        //模板
        if(!StringUtils.isEmpty(modelName)){
            ClassPathResource resource=new ClassPathResource("/template/"+modelName);
            excelWriterBuilder.withTemplate(resource.getInputStream());
        }
        //注册监听器
        if(writeHandler!=null){
            excelWriterBuilder.registerWriteHandler(writeHandler);
        }

        //各类自定义转换器
        if(!org.springframework.util.CollectionUtils.isEmpty(converters)){
            converters.stream().forEach(c->{
                excelWriterBuilder.registerConverter(c);
            });

        }


        ExcelWriter excelWriter=excelWriterBuilder.build();
        WriteSheet writeSheet= EasyExcel.writerSheet().build();
        //摘要信息写入
        if(abstractInfo!=null){
            excelWriter.fill(abstractInfo,writeSheet);
        }
        //核心数据列表写入
        if(!CollectionUtils.isEmpty(dataList)){
            excelWriter.write(dataList,writeSheet);
        }

        excelWriter.finish();
        outputStream.close();

    }

    public static <T,D> InputStream
    writeExcelForOss(D abstractInfo, List<T> dataList
            , WriteHandler writeHandler, String modelName, Boolean needHead, List<Converter> converters) throws Exception{
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ExcelWriterBuilder excelWriterBuilder= EasyExcel.write(os);
        //映射关系
        if(!CollectionUtils.isEmpty(dataList)){
            excelWriterBuilder.head(dataList.get(0).getClass());
        }
        //是否需要头
        if(needHead!=null){
            excelWriterBuilder.needHead(needHead);
        }
        //模板
        if(!StringUtils.isEmpty(modelName)){
            ClassPathResource resource=new ClassPathResource("/template/"+modelName);
            excelWriterBuilder.withTemplate(resource.getInputStream());
        }
        //注册监听器
        if(writeHandler!=null){
            excelWriterBuilder.registerWriteHandler(writeHandler);
        }

        //各类自定义转换器
        if(!org.springframework.util.CollectionUtils.isEmpty(converters)){
            converters.stream().forEach(c->{
                excelWriterBuilder.registerConverter(c);
            });

        }

        ExcelWriter excelWriter=excelWriterBuilder.build();
        WriteSheet writeSheet= EasyExcel.writerSheet().build();
        //摘要信息写入
        if(abstractInfo!=null){
            excelWriter.fill(abstractInfo,writeSheet);
        }
        //核心数据列表写入
        if(!CollectionUtils.isEmpty(dataList)){
            excelWriter.write(dataList,writeSheet);
        }


        excelWriter.finish();

        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        os.close();

        return is;
    }

    /**
     * 导出文件时为Writer生成OutputStream
     */
    private static OutputStream getOutputStream(String fileName, HttpServletResponse response) {
        //创建本地文件
        String filePath = fileName + ".xlsx";
        try {
            fileName = new String(filePath.getBytes(), "ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentType("application/msexcel;charset=UTF-8");//设置类型
            response.setHeader("Pragma", "No-cache");//设置头
            response.setHeader("Cache-Control", "no-cache");//设置头
            response.setDateHeader("Expires", 0);//设置日期头
            return response.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException("创建文件失败！");
        }
    }
}
