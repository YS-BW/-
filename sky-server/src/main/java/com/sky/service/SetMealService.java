package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

/**
 * @author 32770
 */
public interface SetMealService {
    void insertSetMeal(SetmealDTO setmealDTO);

    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);


    void deleteSetMeal(List<Long> ids);

    void updateSetMeal(SetmealDTO setmealDTO);

    SetmealVO getById(Long id);
}
