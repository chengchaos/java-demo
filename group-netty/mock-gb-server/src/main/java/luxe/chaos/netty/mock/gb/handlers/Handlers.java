package luxe.chaos.netty.mock.gb.handlers;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 *
 */
public class Handlers {

    static final int MAX_FRAME_LENGTH = Integer.MAX_VALUE;
    static final int LENGTH_FIELD_OFFSET = 22;
    static final int LENGTH_FIELD_LENGTH = 2;
    static final int LENGTH_ADJUSTMENT = 1;
    static final int INITIAL_BYTES_TO_STRIP = 0;

    private Handlers() {
        super();
    }

    public static LengthFieldBasedFrameDecoder newLengthFieldBasedFrameDecoder() {
        return new LengthFieldBasedFrameDecoder(
                MAX_FRAME_LENGTH,
                LENGTH_FIELD_OFFSET,
                LENGTH_FIELD_LENGTH,
                LENGTH_ADJUSTMENT,
                INITIAL_BYTES_TO_STRIP) {
        };
    }

}
