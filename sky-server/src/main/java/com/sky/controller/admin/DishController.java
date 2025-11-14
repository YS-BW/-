package com.sky.controller.admin;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author 32770
 */
@RequestMapping("/admin/dish")
@RestController
public class DishController {
    @Autowired
    DishService dishService;

    /**
     * 新增菜品
     * @param dto
     * @return
     */
    @PostMapping
    @ApiOperation("新增菜品")
    public Result addDish(@RequestBody DishDTO dto) {
        dishService.addDish(dto);
        return Result.success();
    }
    /**
     * 分页查询菜品
     */
    @GetMapping("/page")
    @ApiOperation("分页查询菜品")
    public Result<PageResult> pageQuery(DishPageQueryDTO dto) {
        PageResult pageResult =  dishService.pageQuery(dto);
        return Result.success(pageResult);
    }
    /**
     * 删除菜品
     */
    @DeleteMapping
    @ApiOperation("删除菜品")
    public Result delete (@RequestParam List<Long> ids) {
        dishService.delete(ids);
        return Result.success();
    }
    /**
     * 根据id查询
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询")
    public Result<DishVO> getById (@PathVariable Long id){
        DishVO dishVO = dishService.getById(id);
        return Result.success(dishVO);
    }
    /**
     * 修改菜品
     */
    @PutMapping
    @ApiOperation("修改菜品")
    public Result update(@RequestBody DishDTO dto) {
        dishService.update(dto);
        return Result.success();
    }
    @GetMapping("/list")
    @ApiOperation("查询菜品列表")
    public Result<List<DishVO>> list(@RequestParam Long categoryId) {
        List<DishVO> list = dishService.list(categoryId);
        return Result.success(list);
    }

}
