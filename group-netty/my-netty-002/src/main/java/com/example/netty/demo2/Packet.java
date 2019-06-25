package com.example.netty.demo2;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/15 0015 下午 1:57 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public abstract class Packet {

    private byte version = 1;

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public abstract byte getComand();
}
