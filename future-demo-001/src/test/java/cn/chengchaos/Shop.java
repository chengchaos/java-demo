package cn.chengchaos;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/1/25 0025 下午 5:12 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class Shop {

    private String name;

    public Shop() {
        super();
    }

    public Shop(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice(String product) {
        return 1.0D;
    }
}
