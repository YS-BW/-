package com.sky.service.impl;

import com.sky.dto.SetmealDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.service.SetMealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class SetMealServiceImp implements SetMealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    SetmealDishMapper setmealdishMapper;
    @Transactional
    @Override
    public void insertSetMeal(SetmealDTO setmealDTO) {
        log.info("新增套餐");
        //1️⃣拷贝属性
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        //2️⃣调用mapper层接口新增套餐
        setmealMapper.insertSetMeal(setmeal);
        //3️⃣获取套餐id=>遍历绑定套餐菜品关系
        Long id = setmeal.getId();
        for (SetmealDish setmealDishes : setmealDTO.getSetmealDishes()){
            setmealDishes.setSetmealId(id);
            setmealdishMapper.insertSetMealDish(setmealDishes);
        }

    }
}
