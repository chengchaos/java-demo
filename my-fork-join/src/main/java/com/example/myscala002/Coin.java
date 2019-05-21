package com.example.myscala002;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/19 0019 下午 8:12 <br />
 * @since 1.1.0
 */
public class Coin {


    private final int id;

    private final boolean weigh;

    public Coin(int id, boolean weigh) {
        this.id = id;
        this.weigh = weigh;
    }

    public static Coin[] fullBag() {
        int length = 100;

        Coin[] coins = new Coin[length];

        for (int i = 0; i < length; i++) {
            boolean weight =  (i % 3) == 0;
            coins[i] = new Coin(i, weight);
        }

        return coins;
    }

    public int getId() {
        return id;
    }

    public boolean isWeigh() {
        return weigh;
    }
}
