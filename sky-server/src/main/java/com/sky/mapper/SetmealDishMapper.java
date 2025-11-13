package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author 32770
 */
@Mapper
public interface SetmealDishMapper {

    @Select("select count(1) from setmeal_dish where dish_id = #{id}")
    int countByDishId(Long id);
}
