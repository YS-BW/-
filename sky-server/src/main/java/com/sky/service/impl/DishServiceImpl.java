package com.sky.service.impl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 32770
 */
@Service
public class DishServiceImpl implements DishService {
    @Autowired
    DishMapper dishMapper;
    @Autowired
    DishFlavorMapper dishFlavorMapper;
    @Autowired
    SetmealDishMapper setmealdishMapper;
    @Override
    @Transactional
    public void addDish(DishDTO dto) {
        Dish dish = new Dish();
        DishFlavor dishFlavor = new DishFlavor();
        //将dto中的数据拷贝到dish实体类中
        BeanUtils.copyProperties(dto, dish);
        //新增dish表数据
        dishMapper.addDish(dish);
        //获取dishId->连接口味表
        Long dishId = dish.getId();
        //将dio中的口味数据导出到口味表中
        List<DishFlavor> dishFlavors = dto.getFlavors();
        //遍历(判空并赋值id)
        if (dishFlavors != null && !dishFlavors.isEmpty()) {
            for (DishFlavor flavor : dishFlavors) {
                flavor.setDishId(dishId);
                dishFlavorMapper.addDishFlavor(flavor);
            }
        }
    }

    @Override
    public PageResult pageQuery(DishPageQueryDTO dto) {
        //设置分页参数
        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        //执行查询
        Page<DishVO> dishes = dishMapper.pageQuery(dto);
        //返回并封装分页结果
        return new PageResult(dishes.getTotal(), dishes.getResult());
    }

    @Override
    @Transactional
    public void delete(List<Long> ids) {

        for (Long id : ids) {
            DishVO dish = dishMapper.getById(id);
            if (dish.getStatus().equals(StatusConstant.ENABLE)) {
                //起售中的菜品不能删除
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
            //查询当前菜品是否关联了套餐
            if(setmealdishMapper.countByDishId(id)> 0){
                throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
            }
            dishFlavorMapper.delete(id);
            dishMapper.delete(id);
        }

    }

    @Override
    public DishVO getById(Long id) {
        // 查询菜品基本信息
        DishVO dishVO = dishMapper.getById(id);
        // 查询关联的口味信息
        if (dishVO != null) {
            List<DishFlavor> flavors = dishFlavorMapper.getByDishId(id);
            dishVO.setFlavors(flavors);
        }
        return dishVO;
    }

    @Override
    @Transactional
    public void update(DishDTO dto) {
        //获取菜品id
        Long dishId = dto.getId();
        //修改dish
        Dish dish = new Dish();
        BeanUtils.copyProperties(dto, dish);
        dishMapper.update(dish);
        //根据dto获取口味数据
        List<DishFlavor> flavors = dto.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            //1️⃣删除旧数据
            dishFlavorMapper.delete(dish.getId());
            for (DishFlavor flavor : flavors) {

                //2️⃣增加新数据
                flavor.setDishId(dishId);
                dishFlavorMapper.addDishFlavor(flavor);
            }
        }
    }

    @Override
    public List<DishVO> list(Long categoryId) {
        return dishMapper.list(categoryId);
    }
}
