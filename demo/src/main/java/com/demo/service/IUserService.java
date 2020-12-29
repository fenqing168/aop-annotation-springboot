package com.demo.service;

import com.demo.entity.UserEntity;

/**
 * @author fenqing
 */
public interface IUserService {
    void add(UserEntity user);

    void update(UserEntity user);
}
