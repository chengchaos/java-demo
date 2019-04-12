package com.example.myscala002.demo1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/11 0011 下午 1:55 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {



    private static final Logger LOGGER = LoggerFactory.getLogger(FirstClientHandler.class);


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        LOGGER.info("{} : 客户端写出数据", new Date());

        String input = "赵客缦胡缨，吴钩霜雪明。银鞍照白马，飒沓如流星。\n";
        // 1: 获取数据
        ByteBuf byteBuf = null;
        for (int i = 1; i<=1000; i++) {
            byteBuf = getByteBuf(ctx, i +" ==> "+ input);
            // 2: 写数据
            ctx.channel().writeAndFlush(byteBuf);

            TimeUnit.SECONDS.sleep(1L);
        }


    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf = (ByteBuf) msg;

        if (LOGGER.isInfoEnabled()) {

            LOGGER.info("{} : 服务端收到：{}", new Date(), byteBuf.toString(CharsetUtil.UTF_8));
        }

        ByteBuf resp = this.createResponse(ctx);

        //ctx.writeAndFlush(resp);

    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx, String input) {
        // 1: 获取 ByteBuf
        ByteBuf byteBuf = ctx.alloc().buffer();

        // 2: 准备数据，指定字符集
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);

        // 3:填充数据到 ByteBuf
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    private ByteBuf createResponse(ChannelHandlerContext ctx) {

        byte[] bytes = "赵客缦胡缨，吴钩霜雪明."
                .getBytes(StandardCharsets.UTF_8);

        ByteBuf byteBuf = ctx.alloc().buffer();

        byteBuf.writeBytes(bytes);

        return byteBuf;
    }
}
