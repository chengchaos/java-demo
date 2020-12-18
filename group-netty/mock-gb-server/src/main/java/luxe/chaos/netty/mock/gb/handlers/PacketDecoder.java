package luxe.chaos.netty.mock.gb.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import luxe.chaos.netty.mock.gb.config.GbConstants;
import luxe.chaos.netty.mock.gb.entities.GbHeader;
import luxe.chaos.netty.mock.gb.entities.GbTboxMessage;
import luxe.chaos.netty.mock.gb.helpers.ByteHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    private static final Logger logger = LoggerFactory.getLogger(PacketDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        if (byteBuf.readableBytes() < GbConstants.TBOX_STANDARD_BOTTOM_LENGTH) {
            return;
        }

        int beginIndex = byteBuf.readerIndex();
        byte[] beginFlag = new byte[GbConstants.HEADER_BEGIN_LENGTH];
        byteBuf.readBytes(beginFlag);
        String beginFlagStr = ByteHelper.toString(beginFlag);
        byteBuf.readByte();
        byte answer = byteBuf.readByte();

        logger.info("## -> {} => {}", GbConstants.HEADER_BEGIN, beginFlagStr);

        if (GbConstants.HEADER_BEGIN.equals(beginFlagStr) &&
                (answer == GbConstants.ANSWER_SUCCESS ||
                        answer == GbConstants.ANSWER_FAULT ||
                        answer == GbConstants.ANSWER_VIN_REPEAT_FAULT ||
                        answer == GbConstants.IS_COMMAND_PACKAGE)) {

            byteBuf.readerIndex(beginIndex);
            byte[] head = new byte[GbConstants.HEADER_MIN_LENGTH];
            byteBuf.readBytes(head);

            GbHeader gbHeader = GbHeader.unwrap(head);
            int bodyLength = gbHeader.getDataUnitLength();
            byte[] body;
            if (byteBuf.readableBytes() >= bodyLength + 1) {
                body = new byte[bodyLength];
                byteBuf.readBytes(body);

            } else {
                body = ByteHelper.EMPTY_BYTE_ARRAY;
                byteBuf.readerIndex(beginIndex);
            }
            byte checkCode = byteBuf.readByte();
            GbTboxMessage gbtBoxMessage = new GbTboxMessage(gbHeader, body, checkCode);
            gbtBoxMessage.setOriginRealtimeBytes(ByteHelper.addAll(gbHeader.getOriginalHeaderBytes(), body, checkCode));
            logger.info("接收到数据 -=> {}", ByteBufUtil.hexDump(gbtBoxMessage.getOriginRealtimeBytes()));
            byteBuf.readerIndex(beginIndex + gbtBoxMessage.getOriginRealtimeBytes().length);
            list.add(gbtBoxMessage);
        } else {
            logger.info("数据不合规，不处理！");
            byteBuf.clear();
        }
    }
}
