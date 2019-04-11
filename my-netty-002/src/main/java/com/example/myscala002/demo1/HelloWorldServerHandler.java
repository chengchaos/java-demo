package com.example.myscala002.demo1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/11 0011 上午 11:25 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class HelloWorldServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldServerHandler.class);


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        LOGGER.info("server channelRead ...");
        ctx.write("server wirte: " + msg);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        ctx.close();
    }
}
