package com.example.netty.demo2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/15 0015 下午 2:27 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {


    private static final Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        LOGGER.info("开始连接！");

        LoginRequestPacket packet = new LoginRequestPacket();

        packet.setUserId(UUID.randomUUID().toString());
        packet.setUsername("flash");
        packet.setPassword("pwd");

        ByteBuf buffer = PacketCodeC.INSTANCE.encode(ctx.alloc(), packet);

        ctx.channel().writeAndFlush(buffer);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginResponsePacket) {

            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

            if (loginResponsePacket.isSuccess()) {
                LoginUtil.markAsLogin(ctx.channel());
                LOGGER.info("登录成功");
            } else {
                LOGGER.error("登录失败");
            }
        }


        if (packet instanceof MessageResponsePacket) {
            MessageResponsePacket resp = (MessageResponsePacket) packet;
            String message = resp.getMessage();
            LOGGER.info("服务器端应答 ==> {}", message);
        }
    }
}
