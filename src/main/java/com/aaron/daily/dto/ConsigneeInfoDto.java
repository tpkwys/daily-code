package com.aaron.daily.dto;



import javax.validation.constraints.NotNull;



public class ConsigneeInfoDto {
    @NotNull(message = "签收人姓名不能为空")
    private String consignee;
    @NotNull(message = "签收人手机号不能为空")
    private String consigneeMobile;
    @NotNull(message = "省不能为空")
    private String consigneeProvince;
    @NotNull(message = "市不能为空")
    private String consigneeCity;
    @NotNull(message = "区不能为空")
    private String consigneeDistrict;
    @NotNull(message = "地址不能为空")
    private String consigneeAddress;
    private String consigneePostcode;
    private String customerNetName;
    private String consigneePhone;
    private String buyerMessage;

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsigneeMobile() {
        return consigneeMobile;
    }

    public void setConsigneeMobile(String consigneeMobile) {
        this.consigneeMobile = consigneeMobile;
    }

    public String getConsigneeProvince() {
        return consigneeProvince;
    }

    public void setConsigneeProvince(String consigneeProvince) {
        this.consigneeProvince = consigneeProvince;
    }

    public String getConsigneeCity() {
        return consigneeCity;
    }

    public void setConsigneeCity(String consigneeCity) {
        this.consigneeCity = consigneeCity;
    }

    public String getConsigneeDistrict() {
        return consigneeDistrict;
    }

    public void setConsigneeDistrict(String consigneeDistrict) {
        this.consigneeDistrict = consigneeDistrict;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public String getConsigneePostcode() {
        return consigneePostcode;
    }

    public void setConsigneePostcode(String consigneePostcode) {
        this.consigneePostcode = consigneePostcode;
    }

    public String getCustomerNetName() {
        return customerNetName;
    }

    public void setCustomerNetName(String customerNetName) {
        this.customerNetName = customerNetName;
    }

    public String getConsigneePhone() {
        return consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }

    public String getBuyerMessage() {
        return buyerMessage;
    }

    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage;
    }

}
