package com.aaron.daily.binary;

import com.aaron.daily.dto.CommonDynamicNumberDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @program: ffc-reconciliation
 * @description:
 * @author: tianpanke
 * @create: 2020-05-27
 **/
public class NumberUtils {
    private static Logger logger= LoggerFactory.getLogger(NumberUtils.class);
    private NumberUtils(){}

    /**
     *  为列表数据生成动态序号,前提列表实体必须继承CommonDynamicNumberDTO
     * @param datas
     * @param start
     * @param <T>
     */
    public static<T> void generateDynamicNumber(List<T> datas, Integer start){
        //列表数据为空 无需生成动态序号
        if(CollectionUtils.isEmpty(datas)){
            return;
        }
        Class cla = (Class) datas.get(0).getClass();
        if(datas.get(0) instanceof CommonDynamicNumberDTO){
            Method method=null;
            try{
                method=cla.getMethod("setDynamicId",Integer.class); //getDeclaredMethod反射不到
                method.setAccessible(true);
                for(T t:datas){
                    method.invoke(t,++start);
                }
            }catch (Exception e){
                logger.warn("===========>设置动态序号失败",e);
            }
        }
    }

    public static Integer setBinary2Zero(Integer target,Integer index){
        //将第i位设置为0
        int mask=~(1<<index);
        return target&mask;
    }
}
