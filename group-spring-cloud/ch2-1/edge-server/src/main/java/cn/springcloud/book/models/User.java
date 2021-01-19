package cn.springcloud.book.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 * </p>
 *
 * @author Cheng, Chao - 1/18/2021 11:39 AM <br />
 * @see [相关类]
 * @since 1.0
 */
@ApiModel("用户登录实体对象")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final User NULL = new User();

    @ApiModelProperty(value = "用户 ID", example = "1")
    private long userId;

    @ApiModelProperty(value = "用户登录名", example = "chengchao", required = true)
    private String username;

    @ApiModelProperty(value = "登录密码", example = "Password!23", required = true)
    private String password;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
