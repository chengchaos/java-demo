package com.example.demo.helper;

import org.junit.jupiter.api.Test;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 3/5/2021 3:26 PM <br />
 * @see [相关类]
 * @since 1.0
 */
public class NonSprintBootMail1Test {

    @Test
    public void test1() {
        try {
            NonSprintBootMail1.sendTextEmail("chengchaos@outlook.com", 112333);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
