package com.demo.aspect.handler;

import cn.fenqing.aop.annotation.annotation.PointcutHandler;
import cn.fenqing.aop.annotation.aspect.AspectHandler;
import cn.fenqing.aop.annotation.constant.HandlerType;
import com.demo.dao.OrderDao;
import com.demo.dao.UserDao;
import com.demo.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author fenqing
 */
@Component
public class UserAspectHandler implements AspectHandler {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserDao userDao;

    @PointcutHandler(flag = "username", type = HandlerType.BEFORE)
    public void test1(UserEntity user) throws InterruptedException {
        Long userId = user.getUserId();
        user = userDao.selectOneByUserId(userId);
        orderDao.updateUserName(user.getUserId(), user.getUsername());
        System.out.println("后置增强");
    }

}
