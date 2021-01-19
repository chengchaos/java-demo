package cn.springcloud.book.controller;

import cn.springcloud.book.models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 * </p>
 *
 * @author Cheng, Chao - 1/18/2021 10:23 AM <br />
 * @see [相关类]
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/v1/user")
@Api(tags="用户接口", description = "用户相关的 REST API")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @PostMapping("/add")
    @ApiOperation("新增用户接口")
    public boolean addUser(@RequestBody User user) {
        return false;
    }

    @GetMapping("/find/{id}")
    public User findById(@PathVariable(value = "id") long id) {
        return User.NULL;
    }

    @PutMapping("/update")
    public boolean update(@RequestBody User user) {
        return true;
    }


    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable(value = "id") long id) {
        return true;
    }
}
