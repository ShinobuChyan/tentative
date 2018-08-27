package com.tentative.api.controller.product;

import com.tentative.common.model.CommonResult;
import com.tentative.core.model.ProductQueryDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Shinobu
 * @since 2018/8/23
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    /**
     * 基础分页查询
     *
     * @param dto dto
     * @return pageResponse
     */
    @PostMapping("/page")
    public CommonResult pageQuery(@RequestBody @Validated ProductQueryDTO dto) {



        return CommonResult.newSuccessResult("查询成功", null, null);
    }

    // profile

    // detail

    // comments

    // add in shopping cart

}
