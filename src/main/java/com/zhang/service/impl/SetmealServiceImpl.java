package com.zhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhang.common.CustomException;
import com.zhang.dto.SetmealDto;
import com.zhang.entity.Setmeal;
import com.zhang.entity.SetmealDish;
import com.zhang.mapper.SetmealMapper;
import com.zhang.service.SetmealDishService;
import com.zhang.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;


    /**
     * 将基本信息和 关联信息保存
     * @param setmealDto
     */
    @Transactional
    @Override
    public void saveWithDish(SetmealDto setmealDto) {

        //保存套餐的基本信息
        this.save(setmealDto);
        //保存套餐的菜品关连信息，操作setmeal_dish 执行insert

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);



    }

    /**
     * 删除套餐和关联数据
     * @param ids
     */
    @Transactional
    @Override
    public void removeWithDish(List<Long> ids) {

        //查询套餐状态 确定是否停售
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);

        int count = this.count(queryWrapper);

        //不能删除
        if (count > 0){
            throw new CustomException("套餐正在售卖中，不能删除");
        }

        //可以删除：
        this.removeByIds(ids);


        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);
        //删除关联数据

        setmealDishService.remove(lambdaQueryWrapper);



    }
}
