package cn.springcloud.book.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 * </p>
 *
 * @author Cheng, Chao - 1/14/2021 11:09 AM <br />
 * @see [相关类]
 * @since 1.0
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final User NULL = new User();

    /**
     * 自定义mongo 主键
     * 加 @Id 注解可自定义主键类型以及自定义自增规则
     * 若不加插入数据数会默认生成 ObjectId 类型的_id 字段
     * org.springframework.data.annotation.Id 包下
     * mongo库主键字段还是为_id 。不必细究(本文实体类中为id）
     */
    private long userId;

    private String userName;

    private String password;

    private List<Role> roles = new ArrayList<>();

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
