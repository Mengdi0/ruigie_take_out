package com.zhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhang.entity.Orders;

public interface OrderService extends IService<Orders> {

    public void submit(Orders orders);









}
