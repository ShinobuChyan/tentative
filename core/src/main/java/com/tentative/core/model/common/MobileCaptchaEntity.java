package com.tentative.core.model.common;

import com.alibaba.fastjson.JSON;

import java.util.Date;

/**
 * 短信验证码实体
 *
 * @author Shinobu
 * @since 2018/8/15
 */
public class MobileCaptchaEntity {

    private String captcha;

    private Date time;

    public MobileCaptchaEntity(String captcha, Date time) {
        this.captcha = captcha;
        this.time = time;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
