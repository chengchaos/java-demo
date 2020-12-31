package luxe.chaos.netty.mock.gb.other;

import org.junit.Test;
import org.slf4j.*;

public class LoggerTest {

    private static final Logger logger = LoggerFactory.getLogger(LoggerTest.class);
    private static final Marker MARKER = MarkerFactory.getMarker("test_marker");

    @Test
    public void testMarker() {


        MDC.put("first", "Dorothy");
        String info = "hello.";

        logger.info(MARKER, "has marker ... she say : {}", info);
        logger.info("no marker ... she say : {}", info);

        // The most beautiful two words in the English language according
        // to Dorothy Parker:
        logger.info("Check enclosed.");
        logger.debug("The most beautiful two words in English.");

        MDC.put("first", "Richard");
        MDC.put("last", "Nixon");
        logger.info("I am not a crook.");
        logger.info("Attributed to the former US president. 17 Nov 1973.");
    }
}
