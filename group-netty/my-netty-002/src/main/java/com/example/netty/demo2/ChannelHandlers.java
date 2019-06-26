package com.example.netty.demo2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
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
 * @author chengchaos[as]Administrator - 2019/4/15 0015 下午 4:57 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class ChannelHandlers {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelHandlers.class);

    public static ChannelInboundHandlerAdapter inA() {
        return new ChannelInboundHandlerAdapter() {

            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                LOGGER.info("InBoundhandler A ==> {}", msg);
                super.channelRead(ctx, msg);
            }
        };
    }

    public static ChannelInboundHandlerAdapter inB() {
        return new ChannelInboundHandlerAdapter() {

            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                LOGGER.info("InBoundhandler B ==> {}", msg);
                super.channelRead(ctx, msg);
            }
        };
    }

    public static ChannelInboundHandlerAdapter inC() {
        return new ChannelInboundHandlerAdapter() {

            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                LOGGER.info("InBoundhandler C ==> {}", msg);
                super.channelRead(ctx, msg);
            }
        };
    }

    public static ChannelOutboundHandlerAdapter outA() {
        return new ChannelOutboundHandlerAdapter() {
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                LOGGER.info("OutBoundHandler A ==> {}", msg);
                super.write(ctx, msg, promise);
            }
        };
    }



    public static ChannelOutboundHandlerAdapter outB() {
        return new ChannelOutboundHandlerAdapter() {
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                LOGGER.info("OutBoundHandler B ==> {}", msg);
                super.write(ctx, msg, promise);
            }
        };
    }

    public static ChannelOutboundHandlerAdapter outC() {
        return new ChannelOutboundHandlerAdapter() {
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                LOGGER.info("OutBoundHandler C ==> {}", msg);
                super.write(ctx, msg, promise);
            }
        };
    }
}
