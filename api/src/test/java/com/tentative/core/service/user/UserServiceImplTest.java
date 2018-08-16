package com.tentative.core.service.user;

import com.tentative.common.constant.StatusConstant;
import com.tentative.common.constant.TypeConstant;
import com.tentative.common.constant.ValueConstant;
import com.tentative.core.entity.User;
import com.tentative.core.service.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author Shinobu
 * @since 2018/8/16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Resource
    private UserService userService;

    @Test
    public void regByCaptcha() {
        User newUser = new User();
        newUser.setId(UUID.randomUUID().toString());
        newUser.setPhoneNumber("123");
        newUser.setType(TypeConstant.USER_TYPE_NORMAL);
        newUser.setStatus(StatusConstant.USER_STATUS_NORMAL);
        newUser.setNickname("123");
        newUser.setCreatorId(ValueConstant.SYSTEM_CREATOR_INFO);
        newUser.setCreatorName(ValueConstant.SYSTEM_CREATOR_INFO);

        userService.regByCaptcha("123", "123");
    }
}