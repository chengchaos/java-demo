package luxe.chaos.netty.mock.gb.helpers;

public class StringHelper {

    private StringHelper() {
        super();
    }

    public static String byte2String(byte b) {
        StringBuilder sb = new StringBuilder(4);
        String v = Integer.toHexString(Byte.toUnsignedInt(b));
        if (v.length() == 1) {
            sb.append("0x0").append(v);
        } else {
            sb.append("0x").append(v);
        }
        return sb.toString();
    }
}
