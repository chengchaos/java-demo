package luxe.chaos.netty.keepalive.client.nio;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.IntBuffer;

public class BufferTest {

    private static final Logger logger = LoggerFactory.getLogger(BufferTest.class);

    static IntBuffer intBuffer = null;

    private void logBuff(IntBuffer intBuffer) {
        logger.info("position = {}", intBuffer.position());
        logger.info("limit = {}", intBuffer.limit());
        logger.info("capacity = {}", intBuffer.capacity());
    }
    @Test
    public void allocateTest() {

        intBuffer = IntBuffer.allocate(20);

        logger.info("-------------- after allocate ----------------");
        logBuff(intBuffer);

        for (int i = 0; i < 5; i++) {
            intBuffer.put(i);
        }
        logger.info("-------------- after put ----------------");
        logBuff(intBuffer);

        intBuffer.flip();
        logger.info("-------------- after flip ----------------");
        logBuff(intBuffer);

        for (int i = 0; i < 3; i++) {
            int j = intBuffer.get();
            logger.info("j => {}", j);
        }
        logger.info("-------------- after get ----------------");
        logBuff(intBuffer);
        Assert.assertNotNull(intBuffer);
    }


}
