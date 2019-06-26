package com.example.myscala002.demo1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/11 0011 下午 2:07 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class FirstServerHandler extends ChannelInboundHandlerAdapter {


    private static final Logger LOGGER = LoggerFactory.getLogger(FirstServerHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        LOGGER.info("{} : 客户端写出数据", new Date());

        // 1: 获取数据
        ByteBuf byteBuf = createResponse(ctx);

        // 2: 写数据
        ctx.channel().writeAndFlush(byteBuf);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf = (ByteBuf) msg;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("{} : 服务端收到：{}", new Date(), byteBuf.toString(CharsetUtil.UTF_8));
        }

        //ByteBuf resp = this.createResponse(ctx);
        //ctx.writeAndFlush(resp);

    }

    private ByteBuf createResponse(ChannelHandlerContext ctx) {

        byte[] bytes = "银鞍照白马，飒沓如流星.".getBytes(CharsetUtil.UTF_8);

        ByteBuf byteBuf = ctx.alloc().buffer();

        byteBuf.writeBytes(bytes);

        return byteBuf;
    }
}
