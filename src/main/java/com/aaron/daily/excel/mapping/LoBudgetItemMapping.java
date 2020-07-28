package com.aaron.daily.excel.mapping;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @program: ffc-budget
 * @description: 物流运营部预算明细映射
 * @author: tianpanke
 * @create: 2020-06-18
 **/
public class LoBudgetItemMapping {

    @ExcelProperty(index = 0)
    private String orgName; //公司名称

    @ExcelProperty(index = 1)
    @NotNull(message = "1")
    private String businessUnitName;//事业部名称

    @ExcelProperty(index = 2)
    @NotNull(message = "2")
    private String warehouseName;//仓库名称

    @ExcelProperty(index = 3)
    private String customerName;//客户名称

    @ExcelProperty(index = 4)
    private String budgetItemsName;//预算项目

    @ExcelProperty(index = 5)
    @NotNull(message = "5")
    @Max(value = 12,message = "5")
    @Min(value = 1,message = "5")
    private Integer budgetMonth; //预算月份

    @ExcelProperty(index = 6)
    @NotNull(message = "6")
    @Digits(integer = 11,fraction = 2,message = "6")
    private BigDecimal budgetTaxlessAmount;//预算金额-未税
    @ExcelIgnore
    private String businessUnitCode;//冗余字段
    @ExcelIgnore
    private String budgetItemsCode;//预算项目编码


    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    
    public String getBusinessUnitName() {
        return businessUnitName;
    }

    public void setBusinessUnitName(String businessUnitName) {
        this.businessUnitName = businessUnitName;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

   
    public String getBudgetItemsName() {
        return budgetItemsName;
    }

    public void setBudgetItemsName(String budgetItemsName) {
        this.budgetItemsName = budgetItemsName;
    }

   
    public Integer getBudgetMonth() {
        return budgetMonth;
    }

    public void setBudgetMonth(Integer budgetMonth) {
        this.budgetMonth = budgetMonth;
    }

    public BigDecimal getBudgetTaxlessAmount() {
        return budgetTaxlessAmount;
    }

    public void setBudgetTaxlessAmount(BigDecimal budgetTaxlessAmount) {
        //剔除末尾无意义0,用于金额长度校验
        if(budgetTaxlessAmount!=null){
            this.budgetTaxlessAmount = budgetTaxlessAmount.stripTrailingZeros();
        }
    }

   
    public String getBusinessUnitCode() {
        return businessUnitCode;
    }

   
    public void setBusinessUnitCode(String businessUnitCode) {
        this.businessUnitCode = businessUnitCode;
    }

   
    public String getBudgetItemsCode() {
        return budgetItemsCode;
    }

   
    public void setBudgetItemsCode(String budgetItemsCode) {
        this.budgetItemsCode = budgetItemsCode;
    }

}
