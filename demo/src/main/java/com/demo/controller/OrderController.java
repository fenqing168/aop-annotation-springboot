package com.demo.controller;

import com.demo.entity.OrderEntity;
import com.demo.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author fenqing
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    private final IOrderService iOrderService;

    public OrderController(IOrderService iOrderService) {
        this.iOrderService = iOrderService;
    }

    @PutMapping("/update")
    public String update(OrderEntity order){
        iOrderService.update(order);
        return "ok";
    }

    @GetMapping("/info/{id}")
    public OrderEntity info(@PathVariable("id") Long orderId){
        return iOrderService.info(orderId);
    }

}
