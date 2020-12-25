package uxe.chaos.dtp.demo;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HashTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(HashTest.class);

    @Test
    public void testHash() {
        int left = 8 -1;
        int right = 3;
        int s1 = left % right;
        LOGGER.info("s1 => {}", s1);

        int s2 = right & left;
        LOGGER.info("s2 => {}", s2);

        for (int i = 0; i < 100; i++) {

            int s = left & i;
            LOGGER.info("i = {}, v => {}", i, s);
        }
    }
}
