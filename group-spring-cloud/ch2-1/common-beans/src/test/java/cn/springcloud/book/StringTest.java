package cn.springcloud.book;

import com.google.common.base.Stopwatch;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 * </p>
 * 看这里： <br />
 * https://www.nowcoder.com/practice/4fe306a84f084c249e4afad5edf889cc?tpId=196&&tqId=37187&rp=1&ru=/activity/oj&qru=/ta/job-code-total/question-ranking
 *
 * @author Cheng, Chao - 1/28/2021 10:07 AM <br />
 * @since 1.0
 */
public class StringTest {
    @Test
    public void parseTest() {
        String a = "abccabc";
        int x = parse(a);
        System.out.println(" x = " + x);

    }

    private int parse(String a) {
        int len = a.length();
        if (len == 0) {
            return 0;
        }

        char f = a.charAt(0);
        int s = 0;
        for (int i = 1; i < len; i++) {
            char c = a.charAt(i);
            if (c == f) {
                s = i;
                break;
            }
        }

        if (s == 0) {
            return 0;
        }

        int max = 0;
        for (int i = 0; (i + s) < len; i++) {

            if (a.charAt(i) == a.charAt(i + s)) {
                max += 1;
            } else {
                break;
            }

        }

        return max * 2;
    }


    @Test
    public void testSolve() {
        String a = "abcdeabcd";
        int x = solve(a);
        System.out.println("x = " + x);
    }


    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     * @param a string字符串 待计算字符串
     * @return int整型
     */
    public int solve(String a) {
        // write code here
        if (a == null || a.length() <= 1) {
            return 0;
        }
        char[] chars = a.toCharArray();
        int maxlen = chars.length / 2; //单个窗口最大长度
        for (int len = maxlen; len >= 1; len--) {
            //a.length()-len-len减两次
            for (int index = 0; index <= a.length() - len - len; index++) {
                if (judge(chars, index, len)) {
                    return len * 2;
                }
            }
        }
        return 0;
    }

    boolean judge(char[] chars, int s, int len) {
        StringBuilder left = new StringBuilder();
        StringBuilder right = new StringBuilder();
        for (int i = s, j = s + len; i < s + len; i++, j++) {
            left.append(chars[i]);
            right.append(chars[j]);
        }
        System.out.println(left + " => " + right);

        for (int i = s; i < s + len; i++) {
            if (chars[i] != chars[i + len]) {
                return false;
            }
        }
        return true;
    }


    @Test
    public void testSolve2() {
        String a;
        int x;
        a = "abcdefgfg";
        x = solve2(a);
        System.out.println("x = " + x);

        a = "abcdeabcd";
        x = solve2(a);
        System.out.println("x = " + x);

        a = "aaaaaaaaaa";
        x = solve2(a);
        System.out.println("x = " + x);

        char[] arr = new char[1000];
        Arrays.fill(arr, 'x');

        x = solve2(new String(arr));
        System.out.println("x = " + x);

        ThreadLocalRandom r = ThreadLocalRandom.current();
        for (int i = 0; i < arr.length; i++) {
            int b = r.nextInt(57);
            b += 65;
            arr[i] = (char)b;
        }
        Stopwatch stopwatch = Stopwatch.createStarted();
        x = solve2(new String(arr));
        stopwatch.stop();
        long elapsedMillis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        System.out.printf("result = %d, time = %d 毫秒", x, elapsedMillis);


    }

    @Test
    public void testChar() {

        System.out.println((int)'a');
        System.out.println((int)'A');
        System.out.println((int)'z');
        System.out.println((int)'Z');

        System.out.println((int)'z' - (int)'A');
    }


    private int solve2(String a) {
        int len = a.length();
        if (len <= 1) {
            return 0;
        }
//        char[] arrays = a.toCharArray();
//        System.out.println(a + " => len = " + len + ", half = " + half);

        int max =  a.length() / 2;
        for (; max >= 1; max--) {
            for (int j = 0; j + max <= len; j++) {
                for (int k = j + max; k + max <= len; k++) {
//                    System.out.println(" i = " + i + ", j = " + j + ", k = " + k);
                    if (match(a, j, k, max)) {
                        return max * 2;
                    }
                }
            }
        }

        return 0;
    }


    private void log(char[] charArray, int firstBegin, int secondBegin, int len) {
        StringBuilder left = new StringBuilder();
        StringBuilder right = new StringBuilder();
        for (int i = firstBegin, j = secondBegin; i < (firstBegin + len); i++, j++) {
            left.append(charArray[i]);
            right.append(charArray[j]);
        }
        System.out.println(String.copyValueOf(charArray));
        System.out.println(left + " => " + right);
    }

    private boolean match(String text, int firstBegin, int secondBegin, int len) {

//        this.log(text, firstBegin, secondBegin, len);

        for (int i = 0; i < len; i++) {
            if (text.charAt(i + firstBegin) != text.charAt(i + secondBegin)) {
                return false;
            }
        }
        return true;
    }
}
