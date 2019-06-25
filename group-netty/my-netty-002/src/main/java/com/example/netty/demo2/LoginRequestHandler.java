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
 * @author chengchaos[as]Administrator - 2019/4/15 0015 下午 5:56 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {



    private static final Logger LOGGER = LoggerFactory.getLogger(LoginRequestHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {

        LOGGER.info("username ==> {}", loginRequestPacket.getUsername());
        LOGGER.info("password ==> {}", loginRequestPacket.getPassword());

        LoginResponsePacket loginResp = new LoginResponsePacket();
        loginResp.setLoginStatus(1);

        ByteBuf loginRespBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResp);
        ctx.channel().writeAndFlush(loginRespBuf);
    }
}
