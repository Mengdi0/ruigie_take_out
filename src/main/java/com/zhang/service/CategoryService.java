package com.zhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhang.entity.Category;

public interface CategoryService extends IService<Category> {


    public void remove(Long id);

}
