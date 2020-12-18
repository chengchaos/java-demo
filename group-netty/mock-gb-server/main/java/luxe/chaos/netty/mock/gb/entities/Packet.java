package luxe.chaos.netty.mock.gb.entities;

/**
 * 用于处理打包和解包的操作总接口
 *
 * @author Jackie Liu
 * @date created in 2018/6/27 11:36
 */
public interface Packet {

    /**
     * 解析国标上行报文数据
     *
     * @param gbBytes 国标上行数据报文
     * @return 解析过的字节数, 可以使用为下一次解析时的读取指针.
     * @throws Exception 解析过程中遭遇的异常
     */
    Integer unwrap(byte... gbBytes) throws Exception;


    /**
     * 将解析过的数据反打包为国标的纯报文二进制数据
     *
     * @return 打包好的国标数据包
     * @throws Exception 数据打包过程中遭遇的异常
     */
    byte[] wrap() throws Exception;

}
