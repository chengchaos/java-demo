package luxe.chaos.unsafe;

import com.google.common.base.Stopwatch;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static luxe.chaos.unsafe.Bytes.*;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author Cheng, Chao - 11/30/2020 2:10 PM <br />
 */
public class UnsafeTest {

    private static final Logger logger = LoggerFactory.getLogger(UnsafeTest.class);
    private static long byteArrayBaseOffset;

    private final long times = 100_000_000L;
    private final String formatter = "%s %d times using %d ms";

    @Test
    public void testArray8() {

        String javaVersion = System.getProperty("java.version");
        logger.info("java version => {}", javaVersion);

        try {
            Field theUnsafe = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            sun.misc.Unsafe UNSAFE = (sun.misc.Unsafe) theUnsafe.get(null);
            logger.info("UNSAFE => {}", UNSAFE);

            byte[] data = new byte[10];
            logger.info("data 1 => {}", Arrays.toString(data));

            byteArrayBaseOffset = UNSAFE.arrayBaseOffset(byte[].class);
            logger.info("byteArrayBaseOffset => {}", byteArrayBaseOffset);

            UNSAFE.putByte(data, byteArrayBaseOffset, (byte) 1);
            UNSAFE.putByte(data, byteArrayBaseOffset + 5, (byte) 5);

            logger.info("data 2 => {}", Arrays.toString(data));

            Assert.assertNotNull("get Unsafe ? ", UNSAFE);
        } catch (Exception e) {
            logger.error("", e);
        }


    }


    @Test
    public void testArray11() {

        String javaVersion = System.getProperty("java.version");
        logger.info("java version => {}", javaVersion);

        try {
//            MethodHandles.Lookup lookup = MethodHandles.lookup();//.defineClass
//
//            jdk.internal.misc.Unsafe UNSAFE = jdk.internal.misc.Unsafe.getUnsafe();
//            logger.info("UNSAFE => {}", UNSAFE);
//
//            byte[] data = new byte[10];
//            logger.info("data 1 => {}", Arrays.toString(data));
//
//            byteArrayBaseOffset = UNSAFE.arrayBaseOffset(byte[].class);
//            logger.info("byteArrayBaseOffset => {}", byteArrayBaseOffset);
//
//            UNSAFE.putByte(data, byteArrayBaseOffset, (byte) 1);
//            UNSAFE.putByte(data, byteArrayBaseOffset + 5, (byte) 5);
//
//            logger.info("data 2 => {}", Arrays.toString(data));
//
//            Assert.assertNotNull("get Unsafe ? ", UNSAFE);
        } catch (Exception e) {
            logger.error("", e);
        }


    }

    @Test
    public void int2bytes2sTest() {
        Stopwatch watch = Stopwatch.createStarted();
        for (long i = 0; i < times; i++) {
            byte[] res = int2bytes2s(Integer.MAX_VALUE, 4);

        }
        watch.stop();
        String result = String.format(formatter, "int2bytes2sTest", times, watch.elapsed(TimeUnit.MILLISECONDS));
        System.out.println(result);
    }


    @Test
    public void int2bytes2uTest() {
        Stopwatch watch = Stopwatch.createStarted();
        for (long i = 0; i < times; i++) {
            byte[] res = int2bytes2u(Integer.MAX_VALUE, 4);
        }
        watch.stop();
        String result = String.format(formatter, "int2bytes2uTest", times, watch.elapsed(TimeUnit.MILLISECONDS));
        System.out.println(result);
    }

    @Test
    public void test2() {

        for (int i = 0; i < 100; i++) {
            int2bytes2sTest();
            int2bytes2uTest();
        }
    }

    @Test
    public void test3() throws InterruptedException {

        ByteBuffer buffer = ByteBuffer.allocate(102400);
        System.out.println("buffer = " + buffer);

        System.out.println("after alocate:"
                + Runtime.getRuntime().freeMemory());

        // 这部分直接用的系统内存，所以对JVM的内存没有影响
        ByteBuffer directBuffer = ByteBuffer.allocateDirect(102400);
        System.out.println("directBuffer = " + directBuffer);
        System.out.println("after direct alocate:"
                + Runtime.getRuntime().freeMemory());
        System.out.println("----------Test wrap--------");
        byte[] bytes = new byte[32];
        buffer = ByteBuffer.wrap(bytes);
        System.out.println(buffer);

        buffer = ByteBuffer.wrap(bytes, 10, 10);
        System.out.println(buffer);

        TimeUnit.SECONDS.sleep(10L);
    }


    @Test
    public void test4() throws Exception {
        System.out.println("--------Test reset----------");
        byte[] bytes = new byte[32];
        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        buffer.clear();
        buffer.position(5);
        buffer.mark();
        buffer.position(10);
        System.out.println("before reset:" + buffer);
        buffer.reset();
        System.out.println("after reset:" + buffer);

        System.out.println("--------Test compact--------");
        buffer.clear();
        buffer.put("abcd".getBytes());
        System.out.println("before compact:" + buffer);
        System.out.println(new String(buffer.array()));

        buffer.flip();
        System.out.println("after flip:" + buffer);
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        System.out.println("after three gets:" + buffer);

        System.out.println("\t" + new String(buffer.array()));
        buffer.compact();
        System.out.println("after compact:" + buffer);
        System.out.println("\t" + new String(buffer.array()));

        System.out.println("------Test get-------------");
        buffer = ByteBuffer.allocate(32);
        buffer.put((byte) 'a').put((byte) 'b').put((byte) 'c').put((byte) 'd')
                .put((byte) 'e').put((byte) 'f');
        System.out.println("before flip()" + buffer);

        // 转换为读取模式
        buffer.flip();
        System.out.println("before get():" + buffer);
        System.out.println((char) buffer.get());
        System.out.println("after get():" + buffer);
    }

    @Test
    public void test5() {
        byte[] bytes = new byte[31];
        String vin = "VINTEST1234567890";
        LocalDateTime ldt = LocalDateTime.of(2020, 11, 30, 17, 49, 50);

        ByteBuffer buff = ByteBuffer.wrap(bytes);
        buff.put(int2byte('#'));
        buff.put(int2byte('#'));
        buff.put(int2byte(2));
        buff.put(int2byte(0xfe));

        for (int i = 0; i < 17; i++) {
            buff.put(int2byte(vin.charAt(i)));
        }

        buff.put(int2byte(1))
                .putShort((short) 6)
                .put(int2byte(ldt.getYear() % 100))
                .put(int2byte(ldt.getMonth().getValue()))
                .put(int2byte(ldt.getDayOfMonth()))
                .put(int2byte(ldt.getHour()))
                .put(int2byte(ldt.getMinute()))
                .put(int2byte(ldt.getSecond()))
                .put(calcBcc(bytes));

        Assert.assertTrue("WTF", checkBbc(bytes));

    }
}