package luxe.chaos.netty.mock.gb.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author Cheng, Chao - 12/16/2020 3:28 PM <br />
 */
@ChannelHandler.Sharable
@Component
public class EchoHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(EchoHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        logger.info(msg.getClass().getName());

        logger.info("receive => {}", msg);

        ByteBuf byteBuf = this.getResponse(ctx);
        ctx.channel().writeAndFlush(byteBuf);

    }

    private ByteBuf getResponse(ChannelHandlerContext ctx) {
        byte[] bytes = "OK\n".getBytes(StandardCharsets.UTF_8);
        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }
}
