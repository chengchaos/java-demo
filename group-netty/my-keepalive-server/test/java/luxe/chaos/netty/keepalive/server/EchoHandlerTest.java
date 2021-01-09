package luxe.chaos.netty.keepalive.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import luxe.chaos.netty.keepalive.server.handler.EchoHandler;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 * </p>
 *
 * @author Cheng, Chao - 1/7/2021 1:57 PM <br />
 * @see [相关类]
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class EchoHandlerTest {

    @Autowired
    private EchoHandler echoHandler;

    @Test
    public void test() throws InterruptedException {
        final StringDecoder stringDecoder = new StringDecoder();
        final StringEncoder stringEncoder = new StringEncoder();

        ChannelInitializer<Channel> ci = new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                channel.pipeline()
                        .addLast(stringEncoder)
                        .addLast(stringDecoder)
                        .addLast(echoHandler);
            }
        };

        EmbeddedChannel channel = new EmbeddedChannel(ci);


        for (int i = 0; i < 10; i++) {

            ByteBuf writeBuf = Unpooled.buffer();
            writeBuf.writeBytes("hello".getBytes(StandardCharsets.UTF_8));
            channel.writeInbound(writeBuf);
            channel.flush();
            writeBuf.clear();

            ByteBuf result = channel.readOutbound();
            System.out.println(convertByteBufToString(result));
            TimeUnit.MILLISECONDS.sleep(100L);
        }


        TimeUnit.SECONDS.sleep(2L);
        System.out.println("end");

        channel.close();
    }


    public String convertByteBufToString(ByteBuf buf) {

        System.out.println("buf has array =>" + buf.hasArray());
        if (buf.hasArray()) {
            // 处理堆缓冲区
            return new String(buf.array(),
                    buf.arrayOffset() + buf.readerIndex(),
                    buf.readableBytes(),
                    StandardCharsets.UTF_8);
        } else {
            // 处理直接缓冲区以及复合缓冲区
            byte[] bytes = new byte[buf.readableBytes()];
            buf.getBytes(buf.readerIndex(), bytes);
            return new String(bytes, 0,
                    buf.readableBytes(),
                    StandardCharsets.UTF_8);
        }
    }
}
