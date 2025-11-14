package com.sky.controller.admin;
import com.sky.dto.SetmealDTO;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    @RequestMapping("")
    public Result addSetMeal(@RequestBody SetmealDTO setmealDTO) {
        log.info("新增套餐");
        setMealService.insertSetMeal(setmealDTO);
        return Result.success();
    }
}
