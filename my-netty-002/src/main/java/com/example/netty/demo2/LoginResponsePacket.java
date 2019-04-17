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
public class LoginResponsePacket extends Packet {



    private int loginStatus = 0;

    public void setLoginStatus(int loginStatus) {
        this.loginStatus = loginStatus;
    }

    public int getLoginStatus() {
        return loginStatus;
    }

    public boolean isSuccess() {
        return loginStatus != 0;
    }

    @Override
    public byte getComand() {
        return Command.LOGIN_RESPONSE;
    }
}
