package cn.chengchaos;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/1/17 0017 下午 8:42 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class Md5Test {

    public static String calcMd5(byte[] input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input);
            byte[] hash = md.digest();
            StringBuilder buff = new StringBuilder(32);
            for (int i = 0, j = hash.length; i < j; i++) {
                int v = hash[i] & 0xFF;
                if (v < 16){
                    buff.append(0);
                }
                buff.append(Integer.toString(v, 16));
            }
            return buff.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.getCause());
        }
    }

    public static String getMd5Plus(String input) {

        return calcMd5(input.getBytes(StandardCharsets.UTF_8));

    }

    /**
     * 对字符串md5加密
     *
     * @param str
     * @return
     */
    public static String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            throw new RuntimeException("MD5加密出现错误");
        }
    }

    public String md5(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");

        md.update(input.getBytes(StandardCharsets.UTF_8));

        BigInteger hash = new BigInteger(1, md.digest());

        return hash.toString(16);
    }

    @Test
    public void md5test() throws NoSuchAlgorithmException {

        String t = "test";
        String r10 = md5(t);
        System.out.println(r10);

        String r11 = getMD5(t);
        System.out.println(r11);
        String r12 = getMd5Plus(t);
        System.out.println(r12);

        String input2 = "admin";

        String r20 = md5(input2);
        String r21 = getMD5(input2);
        String r22 = getMd5Plus(input2);
        System.out.println(r20);
        System.out.println(r21);

        System.out.println(r22);


        String s = DigestUtils.md5Hex(t);
        System.out.println(s);
    }
}
