package com.zhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhang.dto.SetmealDto;
import com.zhang.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    //将基本信息和 关联信息保存
    public void saveWithDish(SetmealDto setmealDto);

    //删除套餐和关联数据
    public void removeWithDish(List<Long> ids);

}

