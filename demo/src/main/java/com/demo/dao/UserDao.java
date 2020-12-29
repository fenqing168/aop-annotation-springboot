package com.demo.dao;

import com.demo.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author fenqing
 */
@Repository
public interface UserDao {
    /**
     * insert
     * @param user user
     */
    void insert(UserEntity user);

    /**
     * update
     * @param user user
     */
    void update(UserEntity user);

    /**
     * id查询用户
     * @param userId 用户id
     * @return 结果
     */
    UserEntity getById(@Param("userId") Long userId);

    UserEntity selectOneByUserId(@Param("userId") Long userId);
}
