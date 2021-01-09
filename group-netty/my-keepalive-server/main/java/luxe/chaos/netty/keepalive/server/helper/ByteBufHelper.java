package luxe.chaos.netty.keepalive.server.helper;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 * </p>
 *
 * @author Cheng, Chao - 1/8/2021 9:47 AM <br />
 * @see [相关类]
 * @since 1.0
 */
public class ByteBufHelper {

    private static final Logger logger = LoggerFactory.getLogger(ByteBufHelper.class);

    public static String byteBuf2String(ByteBuf byteBuf) {
        // 可读取字节数
        int length = byteBuf.readableBytes();

        logger.debug("{} has array => {}, readableBytes => {}",
                byteBuf.getClass(),
                byteBuf.hasArray(),
                length);
        if (byteBuf.hasArray()) {
            // 处理堆缓冲区
            return new String(byteBuf.array(),
                    byteBuf.arrayOffset() + byteBuf.readerIndex(),
                    byteBuf.readableBytes(),
                    StandardCharsets.UTF_8);
        } else {
            // 处理直接缓冲区以及复合缓冲区
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.getBytes(byteBuf.readerIndex(), bytes);
            return new String(bytes, 0,
                    byteBuf.readableBytes(),
                    StandardCharsets.UTF_8);
        }
    }
}
