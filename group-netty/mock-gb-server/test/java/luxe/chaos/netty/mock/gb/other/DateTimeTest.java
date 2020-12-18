package luxe.chaos.netty.mock.gb.other;

import io.netty.buffer.ByteBufUtil;
import luxe.chaos.netty.mock.gb.helpers.ByteHelper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    }
}
