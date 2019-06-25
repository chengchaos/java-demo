package com.example.netty.demo2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
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
 * @author chengchaos[as]Administrator - 2019/4/15 0015 下午 6:04 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {


    private static final Logger LOGGER = LoggerFactory.getLogger(MessageRequestHandler.class);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket requeest) throws Exception {

        LOGGER.info("收到客户端数据 ==> {}", requeest.getMessage());

        MessageResponsePacket resp = new MessageResponsePacket();
        resp.setMessage("OK");
        ByteBuf respBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), resp);
        ctx.channel().writeAndFlush(respBuf);
    }
}
