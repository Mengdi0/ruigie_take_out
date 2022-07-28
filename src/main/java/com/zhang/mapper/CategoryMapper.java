package com.zhang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * 分类管理
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}