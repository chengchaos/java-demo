package com.example.netty.demo2;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/15 0015 下午 1:59 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class LoginRequestPacket extends Packet {




    private String userId;

    private String username;

    private String password;

    public LoginRequestPacket() {
        super();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    @Override
    public byte getComand() {
        return Command.LONIN_REQUEST;
    }
}
