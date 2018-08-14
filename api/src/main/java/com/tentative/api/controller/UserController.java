package com.tentative.api.controller;

import com.tentative.common.model.CommonResult;
import com.tentative.common.model.user.UserCommonDTO;
import com.tentative.core.bean.Mappers;
import com.tentative.core.service.common.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户相关
 *
 * @author Shinobu
 * @since 2018/8/13
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final Mappers mappers;

    private final TokenService tokenService;

    @Autowired
    public UserController(Mappers mappers, TokenService tokenService) {
        this.mappers = mappers;
        this.tokenService = tokenService;
    }

    /**
     * 用户注册
     *
     * @param dto dto
     * @return userInfo
     */
    @PostMapping("/reg")
    public CommonResult register(@RequestBody @Validated UserCommonDTO dto) {
        return CommonResult.newSuccessResult("查询成功", mappers.userMapper.selectAll(), null);
    }

}
