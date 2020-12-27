package luxe.chaos.netty.keepalive.client.docoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2020/12/27 14:49 <br />
 * @see [ 相关类方法 ]
 * @since [ 产品模块版本 ]
 */
public class SimpleIntDecoder extends ByteToMessageDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleIntDecoder.class);


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        while (byteBuf.readableBytes() >= 4) {
            int i = byteBuf.readInt();
            LOGGER.info("解码整数: {}", i);
            list.add(i);
        }
    }
}
