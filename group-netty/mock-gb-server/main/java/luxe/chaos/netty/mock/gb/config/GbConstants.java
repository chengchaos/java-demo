package luxe.chaos.netty.mock.gb.config;

import java.util.HashMap;
import java.util.Map;

public class GbConstants {


    static {
        GbConstants.initStaticResource();
    }
    /**
     * 以下为 “国标协议” -命令单元-命令标识 对应的“上行数据/下行响应数据”解析类在解析 [数据单元] 数据时所需的数据长度集合
     * <p>
     * Note: 如果解析类解析数据所需的长度是固定长度的,就赋予实际的数值,如果是变长的,就赋予一个缺省值: 0
     * Note: 集合元素格式: Key=命令单元-命令标识  :  Value=对应的数据解析方式所需的长度
     * Important Note: 该集合通过静态块具体赋值
     * <p>
     * 公式 16 / 0.75 的由来: 集合默认容量 16 ,但是一旦超过存储因容量因子的 0.75 就默认自动扩容 2 倍数,
     * 为了避免扩容后无用的占用内存 (哪怕是一点都不行), 就提前设定一个大概不会早扩容的值; 命令单元-命令标识 常用的有 11 个,
     * 再给出预留的 5 个, 16 = 11 + 5 就是这么来的,
     */
    public static final Map<Byte, Integer> DECODE_LENGTH_MAP = new HashMap((int) Math.round(16 / 0.75));

    /**
     * 初始化静态资源
     */
    public static void initStaticResource() {

        /**将 “国标协议” -命令单元-命令标识 对应的上行数据解析类解析数据时所需的数据长度集合 {@value #DECODE_LENGTH_MAP} 赋值*/
        // 车辆登入的数据包 [数据单元],长度: 不定长
        DECODE_LENGTH_MAP.put(VEHICLE_LOGIN, 0);

        // 实时信息上报数据包 [数据单元],长度: 不定长
        DECODE_LENGTH_MAP.put(REALTIME_REPORT, 0);

        //  补发实时信息上报数据包 [数据单元],长度: 不定长
        DECODE_LENGTH_MAP.put(REISSUE_REPORT, 0);

        // 车辆登出数据包 [数据单元] 固定长度 8 个字节
        DECODE_LENGTH_MAP.put(VEHICLE_LOGOUT, 8);

        // 平台登入数据包 [数据单元] 固定长度 41 个字节
        DECODE_LENGTH_MAP.put(PLATFORM_LOGIN, 41);

        // 平台登出数据包 [数据单元] 固定长度 8 个字节
        DECODE_LENGTH_MAP.put(PLATFORM_LOGOUT, 8);

        // 心跳 0x07 上行 [数据单元] 0 个字节长度
        DECODE_LENGTH_MAP.put(HEART_BEAT, 0);

        // 终端校时 0x08 上行 [数据单元] 0 个字节长度
        DECODE_LENGTH_MAP.put(TERMINAL_TIMING, 0);

        // 参数查询命令 下行反馈 报文[数据单元]的长度: 不定长
        DECODE_LENGTH_MAP.put(QUERY_COMMAND, 0);

        // 参数设置命令 下行反馈 报文[数据单元]的长度: 不定长
        DECODE_LENGTH_MAP.put(SET_COMMAND, 0);

        // 车载终端控制命令 下行反馈 报文[数据单元]的长度: 不定长
        DECODE_LENGTH_MAP.put(CONTROL_COMMAND, 0);


        /**赋值 {@value REALTIME_DATA_LENGTH_MAP} ,实时信息上报-信息类型标志 的集合*/

        // 整车数据 paramId = 0x01, 长度 length = 20
        REALTIME_DATA_LENGTH_MAP.put(VEHICLE_BASE, 20);

        // 驱动电机数据 paramId = 0x02, 长度: 不定长
        REALTIME_DATA_LENGTH_MAP.put(DRIVE_MOTOR, 0);

        // 燃料电池数据 paramId = 0x03, 长度: 不定长
        REALTIME_DATA_LENGTH_MAP.put(FUEL_CELL, 0);

        // 发动机数据 停车充电过程不传输 paramId = 0x04, 长度 length = 5
        REALTIME_DATA_LENGTH_MAP.put(ENGINE, 5);

        // 车辆位置数据 paramId = 0x05,长度 length = 9
        REALTIME_DATA_LENGTH_MAP.put(VEHICLE_LOCATION, 9);

        // 极值数据 paramId = 0x06, 长度length = 14
        REALTIME_DATA_LENGTH_MAP.put(EXTREMUM, 14);

        // 报警数据 paramId = 0x07,长度: 不定长
        REALTIME_DATA_LENGTH_MAP.put(WARNING, 0);

        // 可充电储能装置电压数据 paramId = 0x08,长度: 不定长
        REALTIME_DATA_LENGTH_MAP.put(ENERGY_STORAGE_VALTAGE, 0);

        // 可充电储能装置温度数据 paramId = 0x09,长度: 不定长
        REALTIME_DATA_LENGTH_MAP.put(ENERGY_STORAGE_TEMP, 0);
        // 0x0A～0x2F 平台交换协议自定义数据 (暂不使用)
        // 0x30～0x7F 预留
        // 0x80～0xFE 用户自定义


        /**加载赋值 “国标协议” -参数查询/设置-参数ID 的集合*/
        PARAM_IDS_AND_VALUES_MAP.put(0x01, "2");
        PARAM_IDS_AND_VALUES_MAP.put(0x02, "2");
        PARAM_IDS_AND_VALUES_MAP.put(0x03, "2");
        PARAM_IDS_AND_VALUES_MAP.put(0x04, "1");
        PARAM_IDS_AND_VALUES_MAP.put(0x05, "String");
        PARAM_IDS_AND_VALUES_MAP.put(0x06, "2");
        PARAM_IDS_AND_VALUES_MAP.put(0x07, "String");
        PARAM_IDS_AND_VALUES_MAP.put(0x08, "String");
        PARAM_IDS_AND_VALUES_MAP.put(0x09, "1");
        PARAM_IDS_AND_VALUES_MAP.put(0x0A, "2");
        PARAM_IDS_AND_VALUES_MAP.put(0x0B, "2");
        PARAM_IDS_AND_VALUES_MAP.put(0x0C, "1");
        PARAM_IDS_AND_VALUES_MAP.put(0x0D, "1");
        PARAM_IDS_AND_VALUES_MAP.put(0x0E, "String");
        PARAM_IDS_AND_VALUES_MAP.put(0x0F, "2");
        PARAM_IDS_AND_VALUES_MAP.put(0x10, "1");
        // 0x11~0x7F 国标预留

    }

    /**
     * 此方法用于重设 “国标协议” 通信中的加密方式 {@link #encryptType}
     *
     * @param encryptType 重设的加密方式
     */
    public static void resetEncryptType(byte encryptType) {
        GbConstants.encryptType = encryptType;
    }


    /**
     * 只为设置 {@link #originRealtimeBytesCutTypeID} 而提供的方法，
     * 当迁移至国家监管平台的数据需要进行调整时，可以调用此方法重设。
     *
     * @param typeId 切割原始数据时的实时信息类型标志ID
     */
    public static void setoriginRealtimeBytesCutTypeId(byte typeId) {
        originRealtimeBytesCutTypeID = typeId;
    }



    /**
     * 国标账号长度
     */
    public static final int GB_USERNAME_LENGTH = 12;

    /**
     * 国标账号密码长度
     */
    public static final int GB_PASSWORD_LENGTH = 20;

    /**
     * --------国标数据包结构- [数据定义] - [起始字节] : [字节长度]--------
     * <p>
     * 数据包结构 - [起始符] - 起始字节
     */
    public static final int HEADER_BEGIN_INDEX = 0;
    /**
     * 数据包结构 - [起始符] - 字节长度
     */
    public static final int HEADER_BEGIN_LENGTH = 2;


    /**
     * 数据包结构 - [命令单元] : [命令标识] - [起始字节]
     */
    public static final int HEADER_COMMAND_INDEX = 2;
    /**
     * 数据包结构 - [命令单元] : [命令标识] - [字节长度]
     */
    public static final int HEADER_COMMAND_LENGTH = 1;


    /**
     * 数据包结构 - [命令单元] : [应答标志] -[起始字节]
     */
    public static final int HEADER_ANSWER_INDEX = 3;
    /**
     * 数据包结构 - [命令单元] : [应答标志] - [字节长度]
     */
    public static final int HEADER_ANSWER_LENGTH = 1;


    /**
     * 数据包结构 - [唯一识别码] - [起始字节]
     */
    public static final int HEADER_VIN_INDEX = 4;
    /**
     * 数据包结构 - [唯一识别码] - [字节长度]
     */
    public static final int HEADER_VIN_LENGTH = 17;

    /**
     * 数据包结构 - [数据单元加密方式] - [起始字节]
     */
    public static final int HEADER_ENCRYPT_INDEX = 21;
    /**
     * 数据包结构 - [数据单元加密方式] - [字节长度]
     */
    public static final int HEADER_ENCRYPT_LENGTH = 1;

    /**
     * 数据包结构 - [数据单元长度] - [起始字节]
     */
    public static final int HEADER_DATA_UNIT_LENGTH_INDEX = 22;
    /**
     * 数据包结构 - [数据单元长度] - [字节长度]
     */
    public static final int HEADER_DATA_UNIT_LENGTH_LENGTH = 2;


    /**
     * 数据包结构 - [数据单元] - [起始字节] Note: 不定长（无字节长度）
     */
    public static final int HEADER_DATA_UNIT_INDEX = 24;


    /**
     * 数据包结构 - [校验码] - [字节长度] Note: 不定下标（无起始字节, [缺省值] 为单次有效数据包末尾）
     */
    public static final int HEADER_CHECK_CODE_LENGTH = 1;


    /**
     * --------以下为 “国标协议” -命令单元-命令标识 ,命令标识 应是发起方的唯一标识--------
     * <p>
     * 为国标协议中定义的固定部分
     * <p>
     * 车辆登入 上行
     */
    public static final byte VEHICLE_LOGIN = 0x01;

    /**
     * 实时信息上报 上行
     */
    public static final byte REALTIME_REPORT = 0x02;

    /**
     * 补发信息上报 上行
     */
    public static final byte REISSUE_REPORT = 0x03;

    /**
     * 车辆登出 上行
     */
    public static final byte VEHICLE_LOGOUT = 0x04;


    /**
     * 平台登入 上行
     */
    public static final byte PLATFORM_LOGIN = 0x05;


    /**
     * 平台登出 上行
     */
    public static final byte PLATFORM_LOGOUT = 0x06;

    /**
     * 心跳 上行
     */
    public static final byte HEART_BEAT = 0x07;


    /**
     * 终端校时 上行
     */
    public static final byte TERMINAL_TIMING = 0x08;


    /**
     * 查询命令 下行
     */
    public static final byte QUERY_COMMAND = (byte) 0x80;


    /**
     * 设置命令 下行
     */
    public static final byte SET_COMMAND = (byte) 0X81;


    /**
     * 车载终端控制命令 下行
     */
    public static final byte CONTROL_COMMAND = (byte) 0X82;

    /**
     * --------以下为 “国标协议” -实时上报信息-类型标志（国标固定的） --------
     * <p>
     * 整车数据 paramId =0x01
     */
    public static final byte VEHICLE_BASE = 0x01;

    /**
     * 驱动电机数据 paramId = 0x02
     */
    public static final byte DRIVE_MOTOR = 0x02;

    /**
     * 燃料电池数据 paramId = 0x03
     */
    public static final byte FUEL_CELL = 0x03;

    /**
     * 发动机数据 停车充电过程不传输 paramId = 0x04
     */
    public static final byte ENGINE = 0x04;

    /**
     * 车辆位置数据 paramId = 0x05
     */
    public static final byte VEHICLE_LOCATION = 0x05;

    /**
     * 极值数据 paramId = 0x06
     */
    public static final byte EXTREMUM = 0x06;

    /**
     * 报警数据 paramId = 0x07
     */
    public static final byte WARNING = 0x07;

    /**
     * 可充电储能装置电压数据 paramId = 0x08
     */
    public static final byte ENERGY_STORAGE_VALTAGE = 0x08;

    /**
     * 可充电储能装置温度数据 paramId = 0x09
     */
    public static final byte ENERGY_STORAGE_TEMP = 0x09;


    /**
     * --------以下为 “国标协议” 车载终端控制命令下发数据格式和定义-控制命令定义（国标固定的） --------
     * <p>
     * 控制命令ID: 0x01 远程升级（OTA - Over The Air）
     * 远程升级说明：根据需要组合升级参数，参数之间用半角分号分隔。
     * 指令如下： “URL 地址;拨号点名称;拨号用户名;拨号密码;地址;端口;
     * 生产厂商代码;硬件版本;固件版本；连接到升级服务器时限”，若某个参数无值，则为空。
     * 远程升级操作建议但不限于采用FTP 方式进行操作。数据定义见7.6.4.5表50远程升级命令数据定义
     */
    public static final byte CMD_OTA = 0x01;


    /**
     * 控制命令ID: 0x02 车载终端关机
     */
    public static final byte CMD_TERMINAL_SHUTDOWN = 0x02;


    /**
     * 控制命令ID: 0x03 车载终端复位 (开机)
     */
    public static final byte CMD_TERMINAL_STARTUP = 0x03;

    /**
     * 控制命令ID: 0x04 车载终端恢复出厂设置
     * 说明：其中包括本地存储时间周期、信息上报时间周期、心跳发送时间周期、终端应答超时时间等。
     */
    public static final byte CMD_DEFAULT_RESET = 0x04;

    /**
     * 控制命令ID: 0x05 断开数据通信链路
     */
    public static final byte CMD_DISCONNECT_COMMUNICATION = 0x05;

    /**
     * 控制命令ID: 0x06 车载终端报警/预警
     */
    public static final byte CMD_TERMINAL_WARNING = 0x06;

    /**
     * 控制命令ID: 0x07 开启抽样监测链路
     */
    public static final byte CMD_SAMPLE_CHECK_COMMUNICATION = 0x07;

    public static final byte FE = (byte) 0xFE;

    public static final byte FF = (byte) 0xFF;

    /**
     * 0x08 ~ 0x7F 为 “国标协议” 官方定义预留
     */


    /**
     * --------车辆数据迁移至国家监管平台时，被迁移的数据在从原始报文中切割出来时所需的实时信息类型分割标志 --------
     * <p>
     * 缺省值: 0x09 （因为通常都是国标的数据需要迁移到国家监控平台）
     * <p>
     * Note:提供重设的接口 {@link #setoriginRealtimeBytesCutTypeId}
     */
    public static byte originRealtimeBytesCutTypeID = 0x09;


    /**
     * 以下为 “国标协议” -参数查询/设置-参数ID 以及参数类型 的集合
     * Note: 通过 {@link #run(String[])} 方法再程序启动时自动加载赋值
     * <p>
     * Key:参数 ID
     * Value:参数类型
     * <p>
     * Note:当value(参数类型表示为)整形时,则将该value赋值为该整形在解析时所需的数据长度
     */
    public static final Map<Integer, String> PARAM_IDS_AND_VALUES_MAP = new HashMap();




    /**
     * “国标协议” -实时信息上报-信息类型标志 ：对应所有实时信息解析类在解析[数据单元]所需字节长度集合
     * <p>
     * Byte : typeId（实时信息解析类的 ID）
     * Integer : 对应的实时信息解析类在解析时所需字节长度
     * <p>
     * 该集合初始化长度公式中 计算因子: 20 的由来，通常国标固定的加上自定义的，长度有个 25 就够了。
     */
    public static final Map<Byte, Integer> REALTIME_DATA_LENGTH_MAP = new HashMap((int) Math.round(25 / 0.75));


    /**
     * --------以下为 “国标协议” 命令单元-应答标志 -------
     * 详细说明 1:
     * 命令的主动发起方应答标志为0xFE，表示此包为命令包；当应答标志不是0xFE 时，被动接
     * 收方应不应答。当命令的被动接收方应答标志不是0xFE，此包表示为应答包。
     * <p>
     * 详细说明 2:
     * 当服务端发送应答时，应变更应答标志，保留报文时间，删除其余报文内容，并重新计算检验位。
     * <p>
     * 成功 	 说明:接收到的信息正确
     */
    public static final byte ANSWER_SUCCESS = 0x01;

    /**
     * 错误 	 说明:设置未成功 或者 解析上行数据错误
     */
    public static final byte ANSWER_FAULT = 0x02;

    /**
     * VIN重复	说明:VIN重复错误
     */
    public static final byte ANSWER_VIN_REPEAT_FAULT = 0x03;

    /**
     * 命令	 说明:表示数据包为命令包，而非应答包
     */
    public static final byte IS_COMMAND_PACKAGE = (byte) 0xFE;


    /**
     * --------以下为 “国标协议” 车联网通信数据加密方式 -------
     * 0x01：数据不加密
     * 0x02：数据经过 RSA 算法加密
     * 0x03:数据经过AES128 位算法加
     */
    public static final byte NO_ENCRYPT = 0x01;
    public static final byte RSA_ENCRYPT = 0x02;
    public static final byte AES128_ENCRYPT = 0x03;

    /**
     * “国标协议”通信中采用的加密方式
     * 默认的加密方式缺省值:{@link #NO_ENCRYPT}
     * Note:提供重设此值的接口 {@link #resetEncryptType(byte)}
     */
    public static byte encryptType = NO_ENCRYPT;





    /**
     * --------以下为 “国标协议” 头信息有效性判断的正则表达式 -------
     * <p>
     * 包含所有 “国标协议” 命令单元-应答标志 的正则表达式,用于判断传入的 应答标志 的有效性
     * <p>
     * 所有的 应答标志 如下,表示格式: byte 16进制 - byte 10进制  - 定义 - 说明
     * 0x01   1         成功         接收到的信息正确
     * 0x02   2         修改错       设置未成功
     * 0x03   3         VIN重复      VIN重复错误
     * 0xFE   -2(负2)   命令         表示数据包为命令包，而非应答包
     */
    public static final String ALL_ANSWER_REGEX = "([1-3]|-2)";


    /**
     * 包含所有 “国标协议” 数据加密方式 的正则表达式,用于判断传入的 数据加密方式  的有效性
     * <p>
     * 所有的 数据加密方式 如下,表示格式: byte 16进制 - byte 10进制  - 定义
     * 0x01   1        数据不加密
     * 0x02   2        数据经过 RSA 算法加密
     * 0x03   3        数据经过AES128 位算法加
     * 0xFE   -2(负2)  表示异常
     * 0xFF   -1(负1)  表示无效
     * 其他预留
     */
    public static final String ENCRYPT_REGEX = "([1-3]|-2|-1)";


    /**
     * --------依据国标协议实际解析工作当中抽象出来的经验值--------
     * <p>
     * TBox 数据包规定最大有效长度峰值（根据实际需求得出最大可能数据长度上限,依此上限值为判断依据,
     * 避免未知因素造成 TBox 单条报文上传大量数据,对于大量数据涌入,有必要做异常判断,不盲目接收
     */
    public static final int TBOX_DEFINATED_MAX_LENGTH = 10000;

    /**
     * 国标协议数据包头信息解析数据初步所需长度,除 [数据单元] 与 [校验码]
     */
    public static final int HEADER_MIN_LENGTH = 24;

    /**
     * 国标协议数据包暂定标准有效长度底值 （头信息解析所需基本长度: 基本头信息长度 + [据采集时间] + 校验码长度）,
     * <p>
     * Note: 默认 基本头信息长度为 {@value #HEADER_MIN_LENGTH}
     * Note: 默认 [数据单元] 长度为 {@value #UPLOADTIME_DATA_LENGTH}
     * Note: 默认 校验码长度 为 {@value #HEADER_CHECK_CODE_LENGTH}
     */
    public static final int TBOX_STANDARD_BOTTOM_LENGTH = HEADER_MIN_LENGTH + HEADER_CHECK_CODE_LENGTH;

    /**
     * 国标协议数据包 [据采集时间] 信息解析数据所需标准长度
     */
    public static final int UPLOADTIME_DATA_LENGTH = 6;

    /**
     * 国标协议数据包头信息 [起始符]
     */
    public static final String HEADER_BEGIN = "##";



}
