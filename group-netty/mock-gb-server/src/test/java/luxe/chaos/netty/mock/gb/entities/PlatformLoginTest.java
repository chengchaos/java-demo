package luxe.chaos.netty.mock.gb.entities;

import io.netty.buffer.ByteBufUtil;
import luxe.chaos.netty.mock.gb.helpers.ByteHelper;
import org.junit.Assert;
import org.junit.Test;

public class PlatformLoginTest {

    @Test
    public void test() {

        String unicode = "";
        String username = "";
        String password = "";

        PlatformLogin platformLogin = new PlatformLogin(unicode, username, password);

        byte[] bytes = platformLogin.getBytes();
        Assert.assertNotNull("why?", bytes);
    }

    @Test
    public void test2() {
        String in = "232305023235353030304c474a474a303030303030010006140c170b201c76";
        byte[] bytes = new byte[in.length() / 2];
        for (int i = 0; i < in.length(); ) {
            String x = in.substring(i, i + 2);
            int y = Integer.parseInt(x, 16);
            bytes[i/2] = (byte) y;
            i += 2;
        }
        String out = ByteBufUtil.hexDump(bytes);

        Assert.assertEquals("why?", out, in);
        byte bbc = ByteHelper.calcBcc(bytes);
        System.out.println(Integer.toHexString(bbc));
    }
}
