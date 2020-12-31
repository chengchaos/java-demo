package luxe.chaos.netty.mock.gb.other;

import io.netty.buffer.ByteBufUtil;
import luxe.chaos.netty.mock.gb.helpers.ByteHelper;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class DateTimeTest {


    private static final Logger logger = LoggerFactory.getLogger(DateTimeTest.class);

    @Test
    public void test1() {
        LocalDateTime ldt = LocalDateTime.now();
        int year = ldt.getYear() % 100;
        int month = ldt.getMonth().getValue();
        int day = ldt.getDayOfMonth();
        int hour = ldt.getHour();
        int minute = ldt.getMinute();
        int second = ldt.getSecond();

        logger.info("year = {}, {}, {}, {}, {}, {}",
                year, month, day,
                hour, minute, second);

        byte[] replyWrapped = new byte[6];
        replyWrapped[0] = ByteHelper.int2byte(ldt.getYear() % 100);
        replyWrapped[1] = ByteHelper.int2byte(month);
        replyWrapped[2] = ByteHelper.int2byte(day);
        replyWrapped[3] = ByteHelper.int2byte(hour);
        replyWrapped[4] = ByteHelper.int2byte(minute);
        replyWrapped[5] = ByteHelper.int2byte(second);

        String dump = ByteBufUtil.hexDump(replyWrapped);
        logger.info("{}", dump);

        Assert.assertNotNull("why?", dump);

    }


    @Test
    public void test3() {
        int[] intArray = new int[]{20, 12, 23, 14, 3, 1, 0, 28, 71, 88, 49, 48, 48, 48, 55, 50, 0, 0, 0, 0, 49, 50, 51, 52, 53, 54, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1};
        byte[] bytes = new byte[intArray.length];
        for (int i = 0; i < intArray.length; i++) {
            bytes[i] = (byte) intArray[i];
        }

        String dump = ByteBufUtil.hexDump(bytes);
        System.out.println(dump);

        String u = ByteHelper.extractString(bytes, 6 + 2, 12, StandardCharsets.US_ASCII);
        System.out.println(u);

        String p = ByteHelper.extractString(bytes, 6 + 2 + 12, 20, StandardCharsets.US_ASCII);
        System.out.println(p);

        Assert.assertEquals("way?", "123456", p);
    }


    @Test
    public void test4() {
        byte b = (byte) 0xFE;

        int x = b & 0xff;
        System.out.println(b+ " " + x);
        System.out.println(Integer.toHexString(b) + " "+ Integer.toHexString(x));
    }


}
