package luxe.chaos.netty.mock.gb.handlers;

import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import luxe.chaos.netty.mock.gb.config.GbConstants;
import luxe.chaos.netty.mock.gb.entities.GbHeader;
import luxe.chaos.netty.mock.gb.entities.GbTboxMessage;
import luxe.chaos.netty.mock.gb.helpers.ByteHelper;
import luxe.chaos.netty.mock.gb.helpers.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@ChannelHandler.Sharable
@Component
public class ResponseHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (Objects.nonNull(msg) && msg instanceof GbTboxMessage) {
            GbTboxMessage message = (GbTboxMessage) msg;
            GbHeader header = message.getGbHeader();
            byte answer = header.getAnswer();
            if (answer == GbConstants.IS_COMMAND_PACKAGE) {
                GbHeader ansHeader = new GbHeader();
                ansHeader.setBegin(header.getBegin());
                ansHeader.setCommandId(header.getCommandId());
                ansHeader.setAnswer(GbConstants.ANSWER_SUCCESS);
                ansHeader.setVin(header.getVin());
                ansHeader.setEncrypt(header.getEncrypt());

                LocalDateTime ldt = LocalDateTime.now();
                byte[] replyWrapped = new byte[24 + 6 + 1];
                replyWrapped[24] = ByteHelper.int2byte(ldt.getYear() % 100);
                replyWrapped[25] = ByteHelper.int2byte(ldt.getMonth().getValue());
                replyWrapped[26] = ByteHelper.int2byte(ldt.getDayOfMonth());
                replyWrapped[27] = ByteHelper.int2byte(ldt.getHour());
                replyWrapped[28] = ByteHelper.int2byte(ldt.getMinute());
                replyWrapped[29] = ByteHelper.int2byte(ldt.getSecond());

                ansHeader.setDataUnitLength(6);
                byte[] headers = ansHeader.wrap();
                System.arraycopy(headers, 0, replyWrapped, 0, 24);

                byte checkCode = ByteHelper.calcBcc(headers);
                replyWrapped[30] = checkCode;

                String replyValue = ByteBufUtil.hexDump(replyWrapped);
                if (logger.isInfoEnabled()) {
                    logger.info("判断是否需要应答，上行命令|应答 => {} | {}, 应答值 => {}",
                            StringHelper.byte2String(ansHeader.getCommandId()),
                            StringHelper.byte2String(answer),
                            replyValue);
                }

                ctx.writeAndFlush(replyWrapped);
            }
            super.channelRead(ctx, msg);
        } else {
            // 不是我们可以处理的数据
            ctx.close();
        }
    }

}
