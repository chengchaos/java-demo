package luxe.chaos.netty.mock.gb.entities;

import io.netty.buffer.ByteBufUtil;
import luxe.chaos.netty.mock.gb.handlers.RequestHandler;
import luxe.chaos.netty.mock.gb.helpers.ByteHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class PlatformLogin {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    String unicode = "";
    String username = "";
    String password = "";

    public PlatformLogin(String unicode, String username, String password) {
        this.unicode = unicode;
        this.username = username;
        this.password = password;
    }

    private byte[] getHeader(String unicode) {

        byte[] header = new byte[24];
        header[0] = (byte) 0x23;
        header[1] = (byte) 0x23;

        header[2] = (byte) 0x05;
        header[3] = (byte) 0xfe;

        byte[] ucBytes = unicode.getBytes(StandardCharsets.US_ASCII);
        System.arraycopy(ucBytes, 0, header, 4, Math.min(ucBytes.length, 17));
        if (logger.isInfoEnabled()) {
            logger.info("ucBytes => {}", ByteBufUtil.hexDump(ucBytes));
        }

        header[21] = (byte) 0x01;
        byte[] dlBytes = ByteHelper.int2bytes2(41);

        if (logger.isInfoEnabled()) {
            logger.info("dlBytes => {}", ByteBufUtil.hexDump(dlBytes));
        }

        header[22] = dlBytes[0];
        header[23] = dlBytes[1];

        if (logger.isInfoEnabled()) {
            logger.info("header => {}", ByteBufUtil.hexDump(header));
        }
        return header;
    }

    private byte[] getBody(int randNum, String username, String password) {
        byte[] body = new byte[41];

        LocalDateTime ldt = LocalDateTime.now();
        body[0] = (byte) (ldt.getYear() % 100);
        body[1] = (byte) (ldt.getMonth().getValue());
        body[2] = (byte) (ldt.getDayOfMonth());
        body[3] = (byte) (ldt.getHour());
        body[4] = (byte) (ldt.getMinute());
        body[5] = (byte) (ldt.getSecond());

        byte[] rnBytes = ByteHelper.int2bytes2(randNum);

        body[6] = rnBytes[0];
        body[7] = rnBytes[1];

        byte[] userBytes = username.getBytes(StandardCharsets.US_ASCII);
        System.arraycopy(userBytes, 0, body, 8, Math.min(userBytes.length, 12));
        if (logger.isInfoEnabled()) {
            logger.info("userBytes => {}", ByteBufUtil.hexDump(userBytes));
        }

        byte[] pwdBytes = password.getBytes(StandardCharsets.US_ASCII);
        System.arraycopy(pwdBytes, 0, body, 20, Math.min(pwdBytes.length, 20));
        if (logger.isInfoEnabled()) {
            logger.info("pwdBytes => {}", ByteBufUtil.hexDump(pwdBytes));
        }

        body[40] = (byte) 0x01;

        if (logger.isInfoEnabled()) {
            logger.info("body => {}", ByteBufUtil.hexDump(body));
        }
        return body;
    }

    private int getRandomNumber() {
        return 1;
    }

    public byte[] getBytes() {
        byte[] head = this.getHeader(unicode);
        byte[] body = this.getBody(getRandomNumber(), username, password);
        byte[] result = new byte[head.length + body.length + 1];

        System.arraycopy(head, 0, result, 0, head.length);
        System.arraycopy(body, 0, result, 24, body.length);

        byte bbc = ByteHelper.calcBcc(result);
        result[result.length -1] = bbc;

        if (logger.isInfoEnabled()) {
            logger.info("result => {}", ByteBufUtil.hexDump(result));
        }
        return result;

    }
}
