package com.example.myscala002;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/19 0019 下午 8:13 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public interface Balance {

    boolean weigh(Coin[] bag, int i);

    class DefaultBalance implements Balance {

        @Override
        public boolean weigh(Coin[] bag, int i) {

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return bag[i].isWeigh();
        }
    }
}
