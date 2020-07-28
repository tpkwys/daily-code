package com.aaron.daily.address;

import com.aaron.daily.dto.ConsigneeInfoDto;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//地址处理
@Component
public class ResolutionUtil {

    public static final String addressRegex="(?<province>[^省]+自治区|.*?省|.*?行政区|.*?京|.*?海|.*?津|.*?庆|.*?湾|)(?<city>[^市]+自治州|.*?地区|.+盟|.*?市|.*?县|.*?岛)(?<district>[^县]+县|.*?区|.*?市|.*?旗|.*?岛|.*?乡)(?<address>.*)";
    public static final String phoneRegex="^1[3|4|5|7|8][0-9]\\d{8}$";
    private Logger logger= LoggerFactory.getLogger(ResolutionUtil.class);

    private static Set<String> level_1= Sets.newHashSet("省","自治区","行政区","京","海","津","庆","湾");
    private static Set<String> level_2= Sets.newHashSet("市","自治州","地区","盟","县","岛");
    private static Set<String> level_3= Sets.newHashSet("县","区","市","旗","岛","乡");

    public ConsigneeInfoDto consigneeResolution(String addressStr, Boolean containsInfoFlag){
        if(StringUtils.isEmpty(addressStr)) {
            throw  new RuntimeException("解析失败！请按正确格式填写！");
        }
        if(!containsInfoFlag){
           return consigneeResolutionNew(addressStr,Boolean.TRUE);
        }
        ConsigneeInfoDto consigneeInfoDto=new ConsigneeInfoDto();
        String[] addressSplits=addressStr.split(";");
        if(addressSplits==null||addressSplits.length<3){
            throw  new RuntimeException("解析失败!请按正确格式填写!");
        }
        consigneeInfoDto.setConsignee(addressSplits[0]);
        if(StringUtils.isEmpty(addressSplits[0])||StringUtils.isEmpty(addressSplits[1])||StringUtils.isEmpty(addressSplits[2])){
            throw  new RuntimeException("解析失败!请按正确格式填写！");
        }
        //手机号格式校验
        if(!Pattern.matches(phoneRegex,addressSplits[1])){
            throw  new RuntimeException("解析失败!手机号格式错误!");
        }
        BeanUtils.copyProperties(consigneeResolutionNew(addressSplits[2],Boolean.TRUE),consigneeInfoDto);
        consigneeInfoDto.setConsignee(addressSplits[0]);
        consigneeInfoDto.setConsigneeMobile(addressSplits[1]);
        return consigneeInfoDto;
    }

    @Deprecated
    public  ConsigneeInfoDto consigneeResolution(Boolean valid,String addressStr){
        if(StringUtils.isEmpty(addressStr)){
            throw  new RuntimeException("解析失败!地址为空无法解析");
        }
        //地址解析
        Matcher matcher= Pattern.compile(addressRegex).matcher(addressStr);
        String province=null,city=null,district=null,address=null;
        while (matcher.find()){
            province=matcher.group("province");
            city=matcher.group("city");
            district=matcher.group("district");
            address=matcher.group("address");
        }
        ConsigneeInfoDto consigneeInfoDto=null;
        if(!valid){
            consigneeInfoDto=new ConsigneeInfoDto();
            consigneeInfoDto.setConsigneeProvince(province);
            consigneeInfoDto.setConsigneeCity(city);
            consigneeInfoDto.setConsigneeDistrict(district);
            consigneeInfoDto.setConsigneeAddress(address);
            return consigneeInfoDto;
        }else{
            if(StringUtils.isEmpty(province)||StringUtils.isEmpty(city)||StringUtils.isEmpty(district)||StringUtils.isEmpty(address)){
                throw  new RuntimeException("解析失败!请确认地址省市区名称与系统名称一致!");
            }
            //地址正确性校验
            String zipCode=null;
            consigneeInfoDto=null;// todo
            if(consigneeInfoDto==null){
                throw  new RuntimeException("解析失败!请确认地址省市区名称与系统名称一致!");
            }
            consigneeInfoDto.setConsigneeAddress(address);
        }
        return consigneeInfoDto;
    }


    //1、支持部分解析
    //2、非贪婪匹配（同级多个分隔符，谁先被查到，以谁为基准进行相应截取）
    public ConsigneeInfoDto consigneeResolutionNew(String addr,Boolean valid){

        if(StringUtils.isEmpty(addr)){
            throw  new RuntimeException("解析失败!地址为空无法解析");
        }

        int minIndex1=9999,minIndex2=9999,minIndex3=9999;
        int splitLen1=-1,splitLen2=-1,splitLen3=-1;
        String province="",city="",district="";
        //省
        for(String leve1:level_1){
            int i=addr.indexOf(leve1);
            if(i<minIndex1&&i!=-1){
                minIndex1=i;
                splitLen1=leve1.length();
            }
        }
        if(splitLen1!=-1){
            province=addr.substring(0,minIndex1+splitLen1);
            addr=addr.substring(minIndex1+splitLen1);
        }

        //市（代码可以复用上面片段，将就用吧）
        for(String level:level_2){
            int i=addr.indexOf(level);
            if(i<minIndex2&&i!=-1){
                minIndex2=i;
                splitLen2=level.length();
            }
        }

        if(splitLen2!=-1){
            city=addr.substring(0,minIndex2+splitLen2);
            addr=addr.substring(minIndex2+splitLen2);
        }

        //县（代码可以复用上面片段，将就用吧）
        for(String level:level_3){
            int i=addr.indexOf(level);
            if(i<minIndex3&&i!=-1){
                minIndex3=i;
                splitLen3=level.length();
            }
        }

        if(splitLen2!=-1){
            district=addr.substring(0,minIndex3+splitLen3);
            addr=addr.substring(minIndex3+splitLen3);
        }

        ConsigneeInfoDto consigneeInfoDto=null;
        if(!valid){
            consigneeInfoDto=new ConsigneeInfoDto();
            consigneeInfoDto.setConsigneeProvince(province);
            consigneeInfoDto.setConsigneeCity(city);
            consigneeInfoDto.setConsigneeDistrict(district);
            consigneeInfoDto.setConsigneeAddress(addr);
            return consigneeInfoDto;
        }else{
            if(StringUtils.isEmpty(province)||StringUtils.isEmpty(city)||StringUtils.isEmpty(district)||StringUtils.isEmpty(addr)){
                throw  new RuntimeException("解析失败!请确认地址省市区名称与系统名称一致!");
            }
            //地址正确性校验
            String zipCode=null;
            consigneeInfoDto=null; // todo
            if(consigneeInfoDto==null){
                throw  new RuntimeException("解析失败!请确认地址省市区名称与系统名称一致!");
            }
            consigneeInfoDto.setConsigneeAddress(addr);
        }
        return consigneeInfoDto;

    }


}