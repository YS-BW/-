package com.sky.controller.admin;


import com.sky.result.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author 32770
 */
@RequestMapping("/admin/shop")
@ApiOperation("店铺管理")
@Slf4j
@RestController("adminShopController")
public class ShopController {

    private static final String KEY = "SHOP_STATUS";
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 修改店铺营业状态
     *
     * @param status
     * @return
     */
    @PutMapping("/{status}")
    @ApiOperation("设置店铺营业状态")
    public Result setStatus(@PathVariable Integer status) {
        log.info("修改店铺营业状态");
        // 保存到Redis中
        redisTemplate.opsForValue().set(KEY, status);
        return Result.success();
    }
    /**
     * 获取店铺营业状态
     *
     * @return
     */
    @GetMapping("/status")
    @ApiOperation("获取店铺营业状态")
    public Result<Integer> getStatus(){
        log.info("获取店铺营业状态");
        return Result.success((Integer) redisTemplate.opsForValue().get(KEY));
    }
}
