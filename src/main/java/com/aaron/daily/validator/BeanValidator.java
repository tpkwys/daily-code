package com.aaron.daily.validator;

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
public class BeanValidator {

    public static <T> List<String> validate(Integer lineNo,T object) {
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
            return StringUtils.isEmpty((String) object);
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
                if (val instanceof String && StringUtils.isEmpty((String) val)) {
                    continue;
                }
                flag = false;
                break;

            }
        }
        return flag;
    }
}
