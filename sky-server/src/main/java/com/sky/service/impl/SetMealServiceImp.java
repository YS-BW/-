package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 32770
 */
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

    @Override
    @ApiOperation("分页查询")
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("分页查询");
        //1️⃣设置分页参数
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        //2️⃣调用mapper层接口
        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);
        //3️⃣返回结果
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    @Transactional
    public void deleteSetMeal(List<Long> ids) {
        for (Long id : ids) {
            Setmeal setmeal = setmealMapper.getById(id);
            //1️⃣删除套餐
            if (!setmeal.getStatus().equals(StatusConstant.ENABLE)){
                //判断是否在售
                setmealMapper.deleteSetMeal(id);
            }

            //2️⃣删除套餐菜品关系
            setmealdishMapper.deleteSetMealDish(id);
        }
    }
}
