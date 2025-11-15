package com.sky.controller.admin;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 32770
 */
@ApiOperation("套餐管理")
@RestController
@Slf4j
@RequestMapping("/admin/setmeal")
public class SetMealController {
    @Autowired
    private SetMealService setMealService;
    @ApiOperation("新增套餐")
    @PostMapping
    public Result addSetMeal(@RequestBody SetmealDTO setmealDTO) {
        log.info("新增套餐");
        setMealService.insertSetMeal(setmealDTO);
        return Result.success();
    }
    @ApiOperation("套餐分页查询")
    @GetMapping("/page")
    public Result<PageResult> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("套餐分页查询");
        PageResult pageResult = setMealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }
    @ApiOperation("删除套餐")
    @DeleteMapping
    public Result deleteSetMeal(@RequestParam List<Long> ids) {
        log.info("删除套餐");
        setMealService.deleteSetMeal(ids);
        return Result.success();
    }
}
