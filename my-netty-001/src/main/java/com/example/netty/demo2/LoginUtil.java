package com.example.netty.demo2;

import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/15 0015 下午 4:08 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class LoginUtil {

    public static void markAsLogin(Channel channel) {

        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
        return loginAttr.get() != null;
    }
}
