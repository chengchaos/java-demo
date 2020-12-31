package luxe.chaos.netty.mock.gb.other;

import io.netty.buffer.*;
import org.junit.Test;

import java.nio.ByteBuffer;

public class ArrayTest {

    @Test
    public void test1() {

        int[] intArray = new int[1];

    }

    @Test
    public void test2() {

        ByteBuf buf = Unpooled.buffer(16);

        System.out.println(buf.capacity());
        buf.writeByte(256);
        System.out.println(buf.capacity());
        buf.writeByte(256);
        System.out.println(buf.capacity());

        System.out.println("bytebuf .. "+ ByteBufUtil.hexDump(buf));

        ByteBuffer buffer = buf.nioBuffer();
        byte[] array = buffer.array();
        System.out.println("buffer.array .. "+ ByteBufUtil.hexDump(array) + ", len = "+ array.length);

        System.out.println(buffer.position());
        System.out.println(buffer.capacity());

        short s = buffer.getShort();
        System.out.println("s = "+ s);
        System.out.println(buffer.position());
        System.out.println(buffer.capacity());


//        buffer.flip();
//        short s = buffer.getShort();
//        System.out.println("s = "+ s);



    }

    @Test
    public void test3() {

        ByteBuf head = Unpooled.buffer(64);
        ByteBuf body = Unpooled.buffer(64);
        head.writeInt(1);
        head.writeInt(2);
        System.out.println("head = "+ ByteBufUtil.hexDump(head));
        body.writeInt(3);
        body.writeInt(4);
        System.out.println("body = "+ ByteBufUtil.hexDump(body));

//        CompositeByteBuf buf = new UnpooledByteBufAllocator(false).compositeBuffer();
//        buf.addComponent(head).addComponent(body);

        CompositeByteBuf buf = (CompositeByteBuf) Unpooled.wrappedBuffer(head, body);


        for (int i = 0; i < 4; i++) {
            int v = buf.readInt();
            System.out.println("v = "+ v);
        }
        String dump = ByteBufUtil.hexDump(buf);
        System.out.println("dump = "+ dump);

        ByteBufHolder holder;
    }
}
