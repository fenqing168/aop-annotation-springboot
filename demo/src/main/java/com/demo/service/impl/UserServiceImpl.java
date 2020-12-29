package com.demo.service.impl;

import cn.fenqing.aop.annotation.annotation.PointcutFlag;
import com.demo.dao.UserDao;
import com.demo.entity.UserEntity;
import com.demo.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @author fenqing
 */
@Service
public class UserServiceImpl implements IUserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void add(UserEntity user) {
        userDao.insert(user);
    }

    @Override
    @PointcutFlag(flag = "username")
    public void update(UserEntity user) {
        userDao.update(user);
    }
}
