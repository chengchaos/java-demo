package cn.springcloud.book.beans;

import java.io.Serializable;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 * </p>
 *
 * @author Cheng, Chao - 1/14/2021 11:12 AM <br />
 * @see [相关类]
 * @since 1.0
 */
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    private long roleId;

    private String roleName;

    private String note;


    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
