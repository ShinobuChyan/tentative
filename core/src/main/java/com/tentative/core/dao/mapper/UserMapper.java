package com.tentative.core.dao.mapper;

import com.tentative.core.dao.BaseMapper;
import com.tentative.core.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

/**
 * @author Shinobu
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * （在非逻辑删除的数据中）根据手机号计数
     *
     * @param phoneNumber 手机号
     * @return count
     */
    int countPhoneNumberInAvailable(@Param("phoneNumber") String phoneNumber);

    /**
     * （在非逻辑删除的数据中）根据用户昵称计数
     *
     * @param nickname 用户昵称
     * @return count
     */
    int countNicknameInAvailable(@Param("nickname") String nickname);

    /**
     * 新增用户记录
     *
     * @param user user
     * @return insert count
     */
    int insertCorrectly(User user);

    /**
     * （在非逻辑删除的数据中）根据ID查询
     *
     * @param id 用户ID
     * @return user
     */
    @Nullable
    User selectAvailableById(@Param("id") String id);

    /**
     * （在非逻辑删除的数据中）根据手机号查询
     *
     * @param phoneNumber 手机号
     * @return user
     */
    @Nullable
    User selectAvailableByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}