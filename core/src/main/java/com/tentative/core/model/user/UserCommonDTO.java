package com.tentative.core.model.user;

import com.alibaba.fastjson.JSON;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 用户相关通用DTO
 *
 * @author Shinobu
 * @since 2018/8/14
 */
public class UserCommonDTO {

    private String id;

    @Pattern(regexp = "^1[0-9]{10}$")
    private String phoneNumber;

    @Pattern(regexp = "^[a-z0-9]+([._\\\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$")
    private String email;

    private String type;

    private String status;

    @Pattern(regexp = "^([\\u4e00-\\u9fa50-9!_,]{2,9})|([A-z0-9!_,]{2,16})$")
    private String nickname;

    @Pattern(regexp = "^[0-9A-z!_,]{6,15}$")
    private String password;

    private Date createTime;

    @Size(max = 64)
    private String captcha;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
