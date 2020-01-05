package luxe.chaos.ostypes;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]E470 - 2019/12/30 11:32 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class OsTypes {

    public static void main(String[] args) {
        String os = System.getProperty("os.name").toLowerCase();

        System.out.println("windows --> "+ os.startsWith("windows"));
        System.out.println("linux --> "+ os.startsWith("linux"));

    }
}
