package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 32770
 */
@Mapper
public interface SetmealDishMapper {

    @Select("select count(1) from setmeal_dish where dish_id = #{id}")
    int countByDishId(Long id);

    @Select("select count(1) from setmeal_dish where setmeal_id = #{id}")
    int countBySetmealId(Long id);


    void insertSetMealDish(SetmealDish setmealDish);

    void deleteSetMealDish(Long id);

    List<SetmealDish> getBySetmealId(Long id);
}
