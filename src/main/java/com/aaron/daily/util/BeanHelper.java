package com.aaron.daily.util;

import com.google.common.base.CaseFormat;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author tianpanke
 * @title: BeanValidator
 * @projectName kshop-transaction
 * @description:
 * @date 2019/10/15 11:37
 */
public class BeanHelper {

    public static <T> List<String> validate(Integer lineNo,T object,Boolean error2Int) {
        //获得验证器
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        //执行验证
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);
        //如果有验证信息，则取出来包装成异常返回
        if (CollectionUtils.isEmpty(constraintViolations)) {
            return new ArrayList<String>();
        }
        return convertErrorMsg(lineNo,constraintViolations);
        //此处不走常规：抛出异常，而是将异常信息以列表形式返回由上层接口统一处理
       // throw new ValidationException(convertErrorMsg(constraintViolations));
    }
    // 数据基本校验，根据二进制 第i位 0/1标识异常
    public static <T> Integer validate(T object,Integer objectLength){
        //获得验证器
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        //执行验证
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);
        Integer flag=(1<<objectLength)-1;
        if (CollectionUtils.isEmpty(constraintViolations)) {
           return flag;
        }
        //如果有验证信息，则取出来包装成异常返回
        return convertErrorMsg(constraintViolations,flag);
    }

    public static <T> Integer convertErrorMsg(Set<ConstraintViolation<T>> set, Integer flag){
        for (ConstraintViolation<T> cv : set) {
            //这里循环获取错误信息，可以自定义格式
            int errorIndex=Integer.parseInt(cv.getMessage());
            //将第i位设置为0
            int mask=~(1<<errorIndex);
            flag=flag&mask;
        }
        return flag;
    }


    public static <T> List<String> convertErrorMsg(Integer lineNo,Set<ConstraintViolation<T>> set) {
        String lineInfo=lineNo==null?"":String.format("[第%s行]",lineNo);
        Map<String, StringBuilder> errorMap = new HashMap<>();
        List<String> result=new ArrayList<>();
        String property;
        for (ConstraintViolation<T> cv : set) {
            //这里循环获取错误信息，可以自定义格式
            result.add(lineInfo+cv.getMessage());
        }
        return result;
    }

    //判断某对象是否为空(null/对象属性为空)
    public static <T> Boolean isBlankObject(T object) throws Exception {
        if (object == null) {return true;}
        if (object instanceof List) {
            return ((List) object).size() == 0;
        }
        if (object instanceof Map) {
            return ((Map) object).size() == 0;
        }
        if (object instanceof String) {
            return StringUtils.isBlank((String) object);
        }
        Class cla = (Class) object.getClass();
        Field[] fs = cla.getDeclaredFields();//得到属性集合
        boolean flag = true;
        //有一个不为空，则返回false
        for (Field f : fs) {
            f.setAccessible(true);//设置属性是可以访问到的
            Object val = f.get(object);
            if (val != null) {
                //list为空 继续
                if (val instanceof List && ((List) val).size() == 0) {
                    continue;
                }

                //map为空  继续
                if (val instanceof Map && ((Map) val).size() == 0) {
                    continue;
                }
                //string为空 继续
                if (val instanceof String && StringUtils.isBlank((String) val)) {
                    continue;
                }
                flag = false;
                break;

            }
        }
        return flag;
    }

    //驼峰 转 “xxx_xxx_xx”  eg: Apple.appleName -> apple.apple_name （对于多表同字段有可能产生bug 请慎重使用）
    public static  String analysisSortField(String str, List<Class> poClassList){
        String afterCaseFormat=null;
        if(StringUtils.isBlank(str)){
            return null;
        }
       Boolean containsFields=false;
        if(CollectionUtils.isNotEmpty(poClassList)){
            for (Class c:poClassList){
                String  poClassName=c.getSimpleName();
                int poIndex=poClassName.lastIndexOf("PO");
                poClassName=poIndex==-1?poClassName:poClassName.substring(0,poIndex);
                Field[] fields=c.getDeclaredFields();
                if(fields!=null&&fields.length>0){
                    for(Field field:fields){
                        if(str.equals(field.getName())){
                              afterCaseFormat= CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,poClassName)+".";
                              containsFields=true;
                              break;
                        }
                    }
                }
            }
        }
        if(containsFields){
            afterCaseFormat=afterCaseFormat+ CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,str);
        }
        return afterCaseFormat;
    }
    
}
