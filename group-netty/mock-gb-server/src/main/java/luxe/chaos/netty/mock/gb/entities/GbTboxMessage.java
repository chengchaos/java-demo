package luxe.chaos.netty.mock.gb.entities;

import luxe.chaos.netty.mock.gb.helpers.ByteHelper;

/**
 * 国标协议数据解析对象容器,可以用于基于 “国标协议” 数据解析前和解析后的,对象或者原始数据的双类型传递.
 *
 * @author Jackie Liu
 * @date created in 2018/8/3 14:30
 */
public class GbTboxMessage {



    /**
     * 终端设备 SN 码
     */
    private String terminalSn;


    /**
     * “国标协议” 头信息解析对象
     */
    private GbHeader gbHeader;

    /**
     * “国标协议” 数据包解析对象
     */
    private Packet packet;


    /**
     * “国标协议” 数据单元数据
     */
    private byte[] gbDataUnit;


    /**
     * “国标协议” - [校验码]
     */
    private byte checkCode;


    /**
     * TBox上报的原始的“实时信息”数据
     */
    private byte[] originRealtimeBytes;


    /**
     * “国标协议” 原始报文数据，该数据是往国家监管平台进行原始数据迁移的所需要的数据
     * Note:该原始报文数据不包含车企自定义的数据
     */
    private byte[] bytesForGbTransfer;


    /**
     * 无参构造
     */
    public GbTboxMessage() {
    }


    /**
     * @param gbHeader   “国标协议” 头信息解析对象
     * @param gbDataUnit “国标协议” - [数据单元] 的原始数据
     * @param checkCode  “国标协议” - [校验码]
     */
    public GbTboxMessage(GbHeader gbHeader, byte[] gbDataUnit, byte checkCode) {
        this.gbHeader = gbHeader;
        this.gbDataUnit = gbDataUnit;
        this.checkCode = checkCode;
    }


    /**
     * @param gbHeader            “国标协议” 头信息解析对象
     * @param packet              “国标协议” 数据解析后的对象接口
     * @param originRealtimeBytes TBox上报的原始的“实时信息”数据
     */
    public GbTboxMessage(GbHeader gbHeader, Packet packet, byte[] originRealtimeBytes) {
        this.gbHeader = gbHeader;
        this.packet = packet;
        this.originRealtimeBytes = originRealtimeBytes;
    }


    /**
     * @param gbHeader            “国标协议” 头信息解析对象
     * @param packet              “国标协议” 数据解析后的对象接口
     * @param originRealtimeBytes “国标协议” 经过切割的转发至国家监管平台的原始报文
     * @param bytesForGbTransfer  “国标协议” 原始报文数据，该数据是往国家监管平台进行原始数据迁移的所需要的数据
     */
    public GbTboxMessage(GbHeader gbHeader, Packet packet, byte[] originRealtimeBytes, byte[] bytesForGbTransfer) {
        this(gbHeader, packet, originRealtimeBytes);
        this.bytesForGbTransfer = bytesForGbTransfer;
    }


    /**
     * @param gbDataUnit “国标协议” 整体报文元数据(包含头信息原报文)
     * @param gbHeader   “国标协议” 头信息解析对象
     * @param packet     “国标协议” 数据解析后的对象接口
     */
    public GbTboxMessage(byte[] gbDataUnit, GbHeader gbHeader, Packet packet) {
        this.gbDataUnit = gbDataUnit;
        this.gbHeader = gbHeader;
        this.packet = packet;
    }


    public byte[] wrapped() {
        if (gbDataUnit == null) {
            gbDataUnit = new byte[0];
        }

        gbHeader.setDataUnitLength(gbDataUnit.length);
        byte[] headBytes = gbHeader.wrap();
        return ByteHelper.addAll(headBytes, gbDataUnit, checkCode);
    }


    /**
     * @return 返回 当前上传数据的终端设备 SN 码
     */
    public String getTerminalSn() {
        return terminalSn;
    }


    /**
     * 设置当前上传数据的终端设备 SN 码
     *
     * @param terminalSn String
     */
    public void setTerminalSn(String terminalSn) {
        this.terminalSn = terminalSn;
    }

    /**
     * @return 返回 “国标协议” 头信息解析对象
     */
    public GbHeader getGbHeader() {
        return gbHeader;
    }


    /**
     * @return 返回 “国标协议”数据包解析对象
     */
    public Packet getPacket() {
        return packet;
    }


    /**
     * @return 返回 “国标协议” 数据单元的数据
     */
    public byte[] getGbDataUnit() {
        return gbDataUnit;
    }

    public GbTboxMessage setOriginRealtimeBytes(byte[] originRealtimeBytes) {
        this.originRealtimeBytes = originRealtimeBytes;
        return this;
    }

    /**
     * @return checkCode 返回 “国标协议” [校验码]
     */
    public byte getCheckCode() {
        return checkCode;
    }


    /**
     * @return 返回 “国标协议” 原始报文，为二进制字节信息
     */
    public byte[] getOriginRealtimeBytes() {
        return originRealtimeBytes;
    }


    /**
     * @return 返回用于转发到国家平台的 “国标协议” 原始报文数据
     */
    public byte[] getBytesForGbTransfer() {
        return bytesForGbTransfer;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}
