package com.tentative.core.bean;

import com.tentative.core.dao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Shinobu
 * @since 2018/8/14
 */
@Component
public class Mappers {

    public final UserMapper userMapper;

    @Autowired
    public Mappers(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
