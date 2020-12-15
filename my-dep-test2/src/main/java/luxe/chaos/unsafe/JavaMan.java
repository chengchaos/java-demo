package luxe.chaos.unsafe;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author Cheng, Chao - 12/9/2020 1:54 PM <br />
 */
public class JavaMan {

    public static void main(String[] args) {

        byte[] times = Bytes.int2bytes2(666);
        long six6 = Bytes.bytes2int(times[0], times[1]);

        System.out.println("six6 => "+ six6);
    }
}
