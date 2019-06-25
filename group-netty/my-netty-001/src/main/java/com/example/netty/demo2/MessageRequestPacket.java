package com.example.netty.demo2;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/15 0015 下午 3:56 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class MessageRequestPacket extends Packet {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public byte getComand() {
        return  Command.MESSAGE_REQUEST;
    }
}
