package com.demo.controller;

import com.demo.entity.UserEntity;
import com.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author fenqing
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService iUserService;

    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @GetMapping("/list")
    public List<UserEntity> list(){
        return null;
    }

    @PostMapping("/add")
    public String add(@RequestBody UserEntity user){
        iUserService.add(user);
        return "ok";
    }

    @PutMapping("/update")
    public String list(@RequestBody UserEntity user){
        iUserService.update(user);
        return "ok";
    }

}
