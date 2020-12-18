package luxe.chaos.netty.mock.gb.entities;

import luxe.chaos.netty.mock.gb.config.GbConstants;
import luxe.chaos.netty.mock.gb.helpers.ByteHelper;

import java.nio.charset.StandardCharsets;

public class GbHeader {


    /**
     * “国标协议” 原始头信息数据
     */
    private byte[] originalHeaderBytes;


    /**
     * 起始符号
     */
    private String begin;

    /**
     * 命令标识
     */
    private byte commandId;

    /**
     * 应答标识
     */
    private byte answer;

    /**
     * 唯一标识码
     */
    private String vin;

    /**
     * 数据加密方式
     */
    private byte encrypt;

    /**
     * 数据单元长度
     */
    private Integer dataUnitLength;


    public byte[] wrap() {
        return begin == null || "".equals(begin) ? null : ByteHelper.addAll(
                //打包起始符
                (byte) 0x23, (byte) 0x23,
                //打包命令标识
                commandId,
                //打包应答标志
                answer,
                //打包车辆 VIN 码
                vin.getBytes(StandardCharsets.UTF_8),
                //打包数据单元加密方式
                encrypt,
                //打包数据单元长度
                ByteHelper.int2bytes2s(dataUnitLength, GbConstants.HEADER_DATA_UNIT_LENGTH_LENGTH)
        );
    }


    public static GbHeader unwrap(byte[] gbBytes) {

        GbHeader bean = new GbHeader();

        //先将 “国标协议” 头信息的原始数据作保留
        bean.originalHeaderBytes = gbBytes;

        //解析起始符
        bean.begin = new String(gbBytes, GbConstants.HEADER_BEGIN_INDEX, GbConstants.HEADER_BEGIN_LENGTH);

        //解析命令标识
        bean.commandId = gbBytes[GbConstants.HEADER_COMMAND_INDEX];

        //解析应答标志
        bean.answer = gbBytes[GbConstants.HEADER_ANSWER_INDEX];

        //解析车辆 VIN 码
        bean.vin = new String(gbBytes, GbConstants.HEADER_VIN_INDEX, GbConstants.HEADER_VIN_LENGTH);

        //解析数据单元加密方式
        bean.encrypt = gbBytes[GbConstants.HEADER_ENCRYPT_INDEX];

        //解析数据单元长度
        bean.dataUnitLength = ByteHelper.bytes2int(gbBytes[22], gbBytes[23]);

        //开始判断 应答标志 有效性. 非规范性注释: 之所以不判断 命令标识 的有效性是因为 命令标识 没有判断的必要
        return bean;
    }

    /**
     * @return 返回 “国标协议”  头信息 - [标识符]
     */
    public String getBegin() {
        return begin;
    }


    /**
     * @return 返回 “国标协议”  头信息 - [命令标识]
     */
    public byte getCommandId() {
        return commandId;
    }

    /**
     * @return 返回 “国标协议”  头信息 - [应答标志]
     */
    public byte getAnswer() {
        return answer;
    }

    /**
     * @return 返回 “国标协议”  头信息 - [vin码（车辆唯一标识符）]
     */
    public String getVin() {
        return vin;
    }


    /**
     * @return 返回 “国标协议”  头信息 - [数据加密方式]
     */
    public byte getEncrypt() {
        return encrypt;
    }


    /**
     * @return 返回 “国标协议”  头信息 - [数据单元长度]
     */
    public Integer getDataUnitLength() {
        return dataUnitLength;
    }


    /**
     * @return 返回 “国标协议” 原始头信息数据
     */
    public byte[] getOriginalHeaderBytes() {
        return originalHeaderBytes;
    }

    @Override
    public String toString() {
        return "GBHeader{" +
                "begin='" + begin + '\'' +
                ", commandId=" + commandId +
                ", answer=" + answer +
                ", vin='" + vin + '\'' +
                ", encrypt=" + encrypt +
                ", dataUnitLength=" + dataUnitLength +
                '}';
    }

    public GbHeader setBegin(String begin) {
        this.begin = begin;
        return this;
    }

    public GbHeader setCommandId(byte commandId) {
        this.commandId = commandId;
        return this;
    }

    public GbHeader setAnswer(byte answer) {
        this.answer = answer;
        return this;
    }

    public GbHeader setVin(String vin) {
        this.vin = vin;
        return this;
    }

    public GbHeader setEncrypt(byte encrypt) {
        this.encrypt = encrypt;
        return this;
    }

    public GbHeader setDataUnitLength(Integer dataUnitLength) {
        this.dataUnitLength = dataUnitLength;
        return this;
    }
}
