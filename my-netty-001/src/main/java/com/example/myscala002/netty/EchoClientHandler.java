package com.example.myscala002.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/10 0010 下午 3:40 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {


    /**
     * 当接收数据时，都会调用这个方法。
     * 由服务器发送的消息可能会被分块接收。
     *
     * channelRead0 可能会被调用多次。
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        System.out.println("Client received: "+ msg.toString(CharsetUtil.UTF_8));
    }

    /**
     * 将在一个连接建立时被调用，
     * 这确保了数据将会被尽肯能快地写入服务器。
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 当被通知 Channel 是活跃的时候， 发送一条消息
        for (int i = 0; i < 2; i++) {

            ctx.writeAndFlush(Unpooled.copiedBuffer("netty rocks!", CharsetUtil.UTF_8));
            TimeUnit.SECONDS.sleep(1L);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
