package com.example.demo.controller;

import cn.futuremove.activelink.common.constant.Result;
import cn.futuremove.activelink.common.constant.ResultCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/5/24 0024 下午 11:43 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
@RestController
public class ClientController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);


    /**
     * 建立转发连接
     *
     * @param id 配置ID主键
     * @return
     */
    @RequestMapping(value = "openClient/{id}")
    @ResponseBody
    public Object openForwardConnect(@PathVariable Integer id) {


        int r = new Random().nextInt(10);

        LOGGER.info("id ==> {}, random ==> {}", id, r);
        switch (r) {
            case 7:
                return Result.getFail(ResultCodeEnum.ERROR_7);
            case 8:
                return Result.getFail(ResultCodeEnum.ERROR_8);
        }
        return Result.getSuccess();


    }

    /**
     * 关闭转发连接
     *
     * @param id 配置ID主键
     * @return
     */
    @RequestMapping(value = "closeClient/{id}")
    @ResponseBody
    public Object closeForwardConnect(@PathVariable Integer id) {

        int r = new Random().nextInt(10);

        LOGGER.info("id ==> {}, random ==> {}", id, r);
        switch (r) {
            case 6:
                return Result.getFail(ResultCodeEnum.ERROR_6);
            case 7:
                return Result.getFail(ResultCodeEnum.ERROR_7);
            case 8:
                return Result.getFail(ResultCodeEnum.ERROR_8);
        }
        return Result.getSuccess();


    }


    @GetMapping(value = "/run-state")
    public Result checkRunState() {


        int r = new Random().nextInt(10);

        LOGGER.info("random ==> {}", r);

        Set<String> userNames;
        if (r > 7) {
            userNames = Collections.emptySet();

        } else {
            userNames = new HashSet<>(Arrays.asList("minanautoool", "guojiautoool"));
        }
        return Result.getSuccess(userNames);





    }
}
