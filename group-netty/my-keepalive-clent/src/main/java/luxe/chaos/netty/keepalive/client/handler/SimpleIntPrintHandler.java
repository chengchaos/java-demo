package luxe.chaos.netty.keepalive.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2020/12/27 14:52 <br />
 * @see [ 相关类方法 ]
 * @since [ 产品模块版本 ]
 */
public class SimpleIntPrintHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleIntPrintHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        Integer i = (Integer) msg;
        LOGGER.info("打印这个整数: {}", i);

    }
}
