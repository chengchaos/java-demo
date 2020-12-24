package luxe.chaos.netty.mock.gb.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import luxe.chaos.netty.mock.gb.entities.PlatformLogin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    String unicode = "";
    String username = "";
    String password = "";

    public RequestHandler(String unicode, String username, String password) {
        this.unicode = unicode;
        this.username = username;
        this.password = password;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        PlatformLogin platformLogin = new PlatformLogin(unicode, username, password);

        byte[] bytes = platformLogin.getBytes();
        logger.info("write and flush.");
        ctx.writeAndFlush(bytes);

    }


}
