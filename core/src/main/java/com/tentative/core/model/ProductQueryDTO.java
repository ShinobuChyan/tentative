package com.tentative.core.model;

import com.alibaba.fastjson.JSON;

/**
 * 商品分页查询用
 *
 * @author Shinobu
 * @since 2018/8/23
 */
public class ProductQueryDTO {

    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品类型
     */
    private String productType;
    /**
     * 价格区间 - 起始
     */
    private String priceFrom;
    /**
     * 价格区间 - 结束
     */
    private String priceTo;
    /**
     * 商户ID
     */
    private String merchantId;
    /**
     * 省份编码
     */
    private String provinceCode;
    /**
     * 城市编码
     */
    private String cityCode;
    /**
     * 区编码
     */
    private String districtCode;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public boolean isEmpty() {
        return productName == null && productType == null && priceFrom == null && priceTo == null &&
                merchantId == null && provinceCode == null && cityCode == null && districtCode == null;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(String priceFrom) {
        this.priceFrom = priceFrom;
    }

    public String getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(String priceTo) {
        this.priceTo = priceTo;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }
}
