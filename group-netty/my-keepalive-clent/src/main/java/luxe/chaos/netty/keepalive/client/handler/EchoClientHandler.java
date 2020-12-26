package luxe.chaos.netty.keepalive.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import luxe.chaos.netty.keepalive.client.business.DataManager;
import luxe.chaos.netty.keepalive.client.business.DataSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
@ChannelHandler.Sharable
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(EchoClientHandler.class);


    private DataManager dataManager;

    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("EchoClientHandler in channelActive ...");
        ByteBuf byteBuf = DataSender.getByteBuf(ctx, "hello");
        ctx.writeAndFlush(byteBuf);

    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof String) {
            logger.info("收到回复 => {}", msg);
        }

        dataManager.newDataSender().send(ctx);
        ctx.pipeline().remove(this);
    }



}
