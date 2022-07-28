package com.zhang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhang.entity.DishFlavor;
import com.zhang.mapper.DishFlavorMapper;
import com.zhang.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorImpl extends ServiceImpl<DishFlavorMapper,DishFlavor> implements DishFlavorService {
}
