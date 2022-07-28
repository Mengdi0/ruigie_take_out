package com.zhang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * 继承mp 获得增删改查方法
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}
