package luxe.chaos.netty.mock.gb.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;

public class ByteHelper {

    private static final Logger logger = LoggerFactory.getLogger(ByteHelper.class);



    private static long byteBaseOffset;



    private static class UnsafeHolder {
        //public static sun.misc.Unsafe UNSAFE; // NOSONAR
    }
    /**
     * A empty string
     */
    public static final String EMPTY_STRING = "";

    /**
     * Constructor
     */
    private ByteHelper() {
        super();
    }



//    static {
//        Field theUnsafe = null;
//        try {
//            theUnsafe = sun.misc.Unsafe.class.getDeclaredField("theUnsafe"); // NOSONAR
//            theUnsafe.setAccessible(true); // NOSONAR
//            UnsafeHolder.UNSAFE = (sun.misc.Unsafe) theUnsafe.get(null);
//            byteBaseOffset = UnsafeHolder.UNSAFE.arrayBaseOffset(byte[].class);
//            logger.info("byteArrayBaseOffset => {}", byteBaseOffset);
//        } catch (Exception e) {
//            logger.error(EMPTY_STRING, e);
//        }
//    }



    /**
     * A empty byte array.
     */
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    public static String toString(byte... args) {
        if (args == null || args.length == 0) {
            return EMPTY_STRING;
        }
        return new String(args);
    }


    public static byte[] int2byteArray(int number) {
        byte[] result = new byte[4];
        result[0] = (byte) ((number >> 24) & 0xFF);
        result[1] = (byte) ((number >> 16) & 0xFF);
        result[2] = (byte) ((number >> 8) & 0xFF);
        result[3] = (byte) ((number) & 0xFF);

        return result;
    }


    /**
     * int 类型转换成 byte 数组
     * <pre>
     * int a = 2088599167
     * int2bytes2u(a, 4) // [124, 125, 126, 127]
     * int2bytes2u(a, 3) // [125, 126, 127]
     * int2bytes2u(a, 2) // [126, 127]
     * int2bytes2u(a, 1) // [127]
     * </pre>
     *
     * @param number int 类型得数组
     * @param len    byte 数组得长度（从低位开始数）
     * @return
     */
    public static byte[] int2bytes2u(int number, int len) {

//        byte[] result = new byte[len];
//        for (int i = len - 1; i >= 0; i--) {
//            UnsafeHolder.UNSAFE.putByte(result, byteBaseOffset + i, (byte) ((number >> ((len - 1 - i) * 8)) & 0xFF));
//        }
//        return result;
        return int2bytes2s(number, len);
    }


    public static byte[] int2bytes2s(int number, int len) {

        byte[] result = new byte[len];
        for (int i = len - 1; i >= 0; i--) {
            result[i] = (byte) ((number >> ((len - 1 - i) * 8)) & 0xFF);
        }
        return result;
    }


    public static byte int2byte(int number) {
        return (byte) (number & 0xFF);
    }


    public static byte[] int2bytes2(int number) {
        return int2bytes2s(number, 2);
    }


    public static byte[] int2bytes4(int number) {
        return int2bytes2s(number, 4);
    }


    public static int byte2int(byte a) {
        return ((int) a) & 0xff;
    }


    public static int bytes2int(byte a, byte b) {
        return ((int) a & 0xff) << 8 | ((int) b & 0xff);
    }


    public static int bytes2int(byte a, byte b, byte c, byte d) {
        return ((int) a & 0xff) << 24 | ((int) b & 0xff) << 16 |
                ((int) c & 0xff) << 8 | ((int) d & 0xff);
    }


    public static long byte2long(byte a) {
        return ((long) a) & 0xffL;
    }


    public static long bytes2long(byte a, byte b) {
        return ((long) a & 0xff) << 8 | (long) b & 0xff;
    }


    public static long bytes2long(byte a, byte b, byte c, byte d) {
        return ((long) a & 0xffL) << 24 | ((long) b & 0xffL) << 16 |
                ((long) c & 0xffL) << 8 | ((long) d & 0xffL);
    }

    public static long bytes2long( // NOSONAR
                                   byte a, byte b, byte c, byte d,
                                   byte e, byte f, byte g, byte h) {
        return ((long) a & 0xffL) << 56 | ((long) b & 0xffL) << 48 |
                ((long) c & 0xffL) << 40 | ((long) d & 0xffL) << 32 |
                ((long) e & 0xffL) << 24 | ((long) f & 0xffL) << 16 |
                ((long) g & 0xffL) << 8 | ((long) h & 0xffL);
    }

    static long bytes2long(byte[] bs) {
        int bytesLength = bs.length;
        if (bytesLength > 1) {
            if ((bytesLength % 2) != 0 || bytesLength > 8) {
                throw new UnsupportedOperationException("not support");
            }
        }
        switch (bytesLength) {
            case 0:
                return 0;
            case 1:
                return (bs[0] & 0xffL);
            case 2:
                return ((bs[0] & 0xffL) << 8 | (bs[1] & 0xff));
            case 4:
                return ((bs[0] & 0xffL) << 24 | (bs[1] & 0xffL) << 16 | (bs[2] & 0xffL) << 8 | (bs[3] & 0xffL));
            case 8:
                return ((bs[0] & 0xffL) << 56 | (bs[1] & 0xffL) << 48 | (bs[2] & 0xffL) << 40 | (bs[3] & 0xffL) << 32 |
                        (bs[4] & 0xffL) << 24 | (bs[5] & 0xffL) << 16 | (bs[6] & 0xffL) << 8 | (bs[7] & 0xffL));
            default:
                throw new UnsupportedOperationException("Not Supported, Your array's length is "+ bytesLength);
        }

    }


    public static byte[] long2byteArray(long number) {
        byte[] result = new byte[8];
        result[0] = (byte) ((number >> 56) & 0xFFL);
        result[1] = (byte) ((number >> 48) & 0xFFL);
        result[2] = (byte) ((number >> 40) & 0xFFL);
        result[3] = (byte) ((number >> 32) & 0xFFL);
        result[4] = (byte) ((number >> 24) & 0xFFL);
        result[5] = (byte) ((number >> 16) & 0xFFL);
        result[6] = (byte) ((number >> 8) & 0xFFL);
        result[7] = (byte) (number & 0xFFL);

        return result;
    }

    public static byte[] long2bytes(long number, int len) {
        byte[] result = new byte[len];

        for (int i = len - 1; i >= 0; i--) {
            result[i] = (byte) ((number >> ((len - 1 - i) * 8)) & 0xFFL);
        }
        return result;
    }

    public static byte[] long2bytes2(long number) {
        return long2bytes(number, 2);
    }

    public static byte[] long2bytes4(long number) {
        return long2bytes(number, 4);
    }

    public static byte[] long2bytes6(long number) {
        return long2bytes(number, 6);
    }

    public static byte[] long2bytes8(long number) {
        return long2bytes(number, 8);
    }

    public static byte calcBcc(byte[] original, int begin, int len) {
        byte bcc;
        bcc = original[begin];
        for (int i = (begin + 1); i < len; i++) {
            bcc = (byte) (bcc ^ original[i]);
        }
        return bcc;
    }

    public static byte calcBcc(byte[] original) {
        return calcBcc(original, 0, original.length - 1);
    }

    public static boolean checkBbc(byte[] original) {
        byte calc = calcBcc(original);
        byte curr = original[original.length - 1];
        return (calc == curr);
    }

    public static void setBc(byte[] original) {
        byte bbc = calcBcc(original);
        original[original.length - 1] = bbc;
    }


    /**
     * 将给定的 byte 或者 byte[] 拼接成一个 byte[]
     * <p>
     * Important Note: 调用此方法 {@code Object... objs} 元素须为 byte[] / byte / null,其他元素不接受
     * <p>
     * Note: 当 Object... objs 传入的某个元素 ==null 时,则直接忽略跳过,直接进入下一个元素的拼接
     * Warning: 要求进入到这个方法的可变参数 objs != null && objs.length > 1 ,否则无意义
     *
     * @param objs
     * @return byte[]
     */
    public static byte[] addAll(Object... objs)  {
        String thisMetodSign = "addAll(Object... objs)";

        if (objs == null) {
            return EMPTY_BYTE_ARRAY;
        }

        if (objs.length < 1) {
            return EMPTY_BYTE_ARRAY;
        }

        int length = 0;
        int index = 0;
        for (Object obj : objs) {
            if (obj != null) {
                length += obj.getClass().isArray() ? ((byte[]) obj).length : 1;
            }
        }
        byte[] dataPacket = new byte[length];
        for (Object obj : objs) {
            if (obj == null) {
                continue;
            }
            if (obj.getClass().isArray()) {
                byte[] arr = (byte[]) obj;
                System.arraycopy(arr, 0, dataPacket, index, arr.length);
                index += arr.length;
            } else {
                dataPacket[index++] = (byte) obj;
            }
        }
        return dataPacket;
    }

    public static String extractString(byte[] bytes, int offset, int length, Charset charset) {
        if (bytes == null || bytes.length == 0) {
            return EMPTY_STRING;
        }

        String u = new String(bytes, offset, length, charset);
        StringBuilder sb =  new StringBuilder(length);
        for (int i = 0; i < u.length(); i++) {
            char c = u.charAt(i);
            if (c == '\0') {
                break;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * Channel copy method 1. <br />
     * This method copies data from the src channel and wirtes it to
     * the dest channel until EOF on src.
     * This implementation makes use of compact() on the temp buffer
     * to pack down the data if the buffer wasn't fully drained.
     * this may result in data copying, but minimizes system calls.
     * It also requires a cleanup loop to make sure all the data gets sent.
     *
     * @param src ReadableByteChannel
     * @param dest WritableByteChannel
     * @throws IOException
     */
    public static void copyChannel1(ReadableByteChannel src, WritableByteChannel dest) throws IOException {

        ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
        while (src.read(buffer) != -1) {
            // Prepare the buffer to bo drained
            buffer.flip();
            dest.write(buffer);
            // If partial transfer, shift remainder down
            // If buffer is empty, same as doing clear()
            buffer.compact();
        }
        // EOF will leave buffer in fill state
        buffer.flip();
        // Make sure that the buffer is fully drained.
        while (buffer.hasRemaining()) {
            dest.write(buffer);
        }
    }


    /**
     * Channel copy method 2. <br />
     * This method performs the same copy, but
     * assures the temp buffer is empty before reading more data.
     * This never rquires data copying but may result in more systems calls.
     * No post-loop cleanup is needed because the buffer will be empty
     * when the loop is exited.
     * @param src ReadableByteChannel
     * @param dest WritableByteChannel
     * @throws IOException
     */
    public static void copyChannel2(ReadableByteChannel src, WritableByteChannel dest) throws IOException {

        ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
        while (src.read(buffer) != -1) {
            buffer.flip();
            // Make sure that the buffer was fully drained.
            while (buffer.hasRemaining()) {
                dest.write(buffer);
            }
            // Make the buffer empty, ready for filling
            buffer.clear();
        }
    }
}