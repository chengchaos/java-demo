package luxe.chaos.netty.keepalive.client.business;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class DataSender {

    private static final Logger logger = LoggerFactory.getLogger(DataSender.class);

    private final DataManager dataManager;

    public DataSender(DataManager dataManager) {
        this.dataManager = dataManager;
    }



    public void send(ChannelHandlerContext ctx) {
        new Thread(() -> {
            String template = "This is 间隔 %d 分中后发送の";
            long next = 1;
            for (int i = -1; i < 1000; i++) {
                try {
                    logger.info("休眠一下 {} 分钟", next);
                    TimeUnit.MINUTES.sleep(next);
                    String message = String.format(template, next);
                    logger.info(message);
                    ByteBuf byteBuf = getByteBuf(ctx, message);
                    Channel channel = ctx.channel();

                    boolean isOpen = channel.isOpen();
                    boolean isRegistered = channel.isRegistered();
                    boolean isActive = channel.isActive();
                    boolean isWritable = channel.isWritable();

                    logger.info("is isOpen => {}", isOpen);
                    logger.info("is isRegistered => {}", isRegistered);
                    logger.info("is active => {}", isActive);
                    logger.info("is writable => {}", isWritable);

                    if (isOpen && isRegistered && isActive && isWritable) {
                        channel.writeAndFlush(byteBuf);
                        next += 1;
                    }  else {
                        logger.info("ctx close ... ");
                        ctx.close();
                        this.dataManager.reConnect();
                        break;
                    }
                } catch (InterruptedException e) {
                    // An existing connection was forcibly closed by the remote host
                    logger.error("", e);
//                    if (Thread.interrupted())  // Clears interrupted status!
//                        throw new InterruptedException();
                    break;
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
        }).start();
    }

    public static ByteBuf getByteBuf(ChannelHandlerContext ctx, String message) {
        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeBytes(message.getBytes(StandardCharsets.UTF_8));
        return byteBuf;
    }


}
