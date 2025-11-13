package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 32770
 */
@Mapper
public interface DishFlavorMapper {

    void addDishFlavor(DishFlavor flavor);

    void delete(Long dishId);

    // 添加根据菜品ID查询口味列表的方法
    @Select("select * from dish_flavor where dish_id = #{dishId}")
    List<DishFlavor> getByDishId(Long dishId);
}
