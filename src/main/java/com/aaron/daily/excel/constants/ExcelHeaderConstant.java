package com.aaron.daily.excel.constants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author chenrui
 * @create 2020/6/18 15:48
 * @Describe 文件头常量
 */
public class ExcelHeaderConstant {

    public final static Map<String,List<String>> excelHeader = new HashMap<String,List<String>>(){
        private static final long serialVersionUID = -4658617486754142674L;

        //电商
        {put("DS", Arrays.asList("公司名称","事业部名称","客户名称","产品线名称","预算项目","月度","预算金额-未税（元）","预算金额-含税（元）"));}
        //KA
        {put("KA",Arrays.asList("公司名称","事业部名称","客户名称","产品线名称","预算项目","月度","预算金额-未税（元）"));}
        //职能
        {put("ZN",Arrays.asList("公司名称","事业部名称","二级部门","三级部门","预算项目","项目说明","月度","预算金额-未税(元）"));}
        //职能物流
        {put("WL",Arrays.asList("公司名称","事业部名称","仓库名称","客户名称","预算项目","月度","预算金额-未税（元）"));}

    };
}
