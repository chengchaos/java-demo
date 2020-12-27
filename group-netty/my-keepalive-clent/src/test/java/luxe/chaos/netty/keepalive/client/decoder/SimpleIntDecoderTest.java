package luxe.chaos.netty.keepalive.client.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import luxe.chaos.netty.keepalive.client.docoder.SimpleIntDecoder;
import luxe.chaos.netty.keepalive.client.handler.SimpleIntPrintHandler;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2020/12/27 14:55 <br />
 * @see [ 相关类方法 ]
 * @since [ 产品模块版本 ]
 */
public class SimpleIntDecoderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleIntDecoderTest.class);

    @Test
    public void test() {

        LOGGER.info("running ....");
        ChannelInitializer<EmbeddedChannel> ci = new ChannelInitializer<EmbeddedChannel>() {

            @Override
            protected void initChannel(EmbeddedChannel channel) throws Exception {
                channel.pipeline()
                        .addLast("decoder", new SimpleIntDecoder())
                        .addLast("handler", new SimpleIntPrintHandler())
                        ;
            }
        };

        EmbeddedChannel embeddedChannel = new EmbeddedChannel(ci);

        for (int j = 0; j < 100; j++) {
            ByteBuf buf = Unpooled.buffer();
            buf.writeInt(j);
            embeddedChannel.writeInbound(buf);
        }

        try {
            TimeUnit.MINUTES.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
