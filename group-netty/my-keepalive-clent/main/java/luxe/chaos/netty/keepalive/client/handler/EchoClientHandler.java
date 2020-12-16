package luxe.chaos.netty.keepalive.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author Cheng, Chao - 12/16/2020 5:03 PM <br />
 */
@Component
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(EchoClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("EchoClientHandler in channelActive ...");
        ByteBuf byteBuf = this.getByteBuf(ctx, "hello");
        ctx.writeAndFlush(byteBuf);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof String) {
            logger.info("收到回复 => {}", msg);
        }
        String template = "This is 间隔 %d 分中后发送の";
        long next = 1;
        for (int i = 0; i < 100; i++) {
            TimeUnit.MINUTES.sleep(next);
            String message = String.format(template, next);
            logger.info(message);
            ByteBuf byteBuf = this.getByteBuf(ctx, message);
            ctx.writeAndFlush(byteBuf);
            next += 1;
        }
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx, String message) {
        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeBytes(message.getBytes(StandardCharsets.UTF_8));
        return byteBuf;
    }
}
