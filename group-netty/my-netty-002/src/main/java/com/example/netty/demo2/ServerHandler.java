package com.example.netty.demo2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
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
 * @author chengchaos[as]Administrator - 2019/4/15 0015 下午 3:02 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {


    private static final Logger LOGGER = LoggerFactory.getLogger(ServerHandler.class);


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof ByteBuf) {

            ByteBuf byteBuf = (ByteBuf) msg;

            String hexDump = ByteBufUtil.hexDump(byteBuf);

            LOGGER.info("hexDump ==> {}", hexDump);

            Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

            if (packet instanceof  LoginRequestPacket) {
                LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
                LOGGER.info("username ==> {}", loginRequestPacket.getUsername());
                LOGGER.info("password ==> {}", loginRequestPacket.getPassword());

                LoginResponsePacket loginResp = new LoginResponsePacket();
                loginResp.setLoginStatus(1);

                ByteBuf loginRespBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResp);
                ctx.channel().writeAndFlush(loginRespBuf);

            }

            if (packet instanceof MessageRequestPacket){
                MessageRequestPacket requeest = (MessageRequestPacket) packet;
                LOGGER.info("收到客户端数据 ==> {}", requeest.getMessage());

                MessageResponsePacket resp = new MessageResponsePacket();
                resp.setMessage("OK");
                ByteBuf respBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), resp);
                ctx.channel().writeAndFlush(respBuf);
            }
        } else {

            LOGGER.warn("msg not instanceof ByteBuf ==> {}", msg);
            super.channelRead(ctx, msg);

        }

    }
}
