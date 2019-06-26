package cn.futuremove.activelink.common.constant;

/**
 * Created by Littl on 2018/7/11.
 */
public enum ResultCodeEnum {
    SUCCESS("1000", "SUCCESS"),
    ERROR_1("1001", "参数不能为空"),
    ERROR_2("1002", "注册信息失败"),
    ERROR_3("1003", "手机号已存在"),
    ERROR_4("1004", "修改信息失败"),
    ERROR_5("1005", "查询信息失败"),
    ERROR_6("1006", "查询信息不存在"),
    ERROR_7("1007", "系统错误！"),
    ERROR_8("1008", "连接不存在！"),
    ERROR_9("1009", "连接已存在！"),
    ERROR_10("1010", "应用已被注册"),
    ERROR_11("1011", "请刷新信息后重新修改"),
    ERROR_12("1012", "信息不存在"),
    ERROR_13("1013", "添加信息失败"),
    ERROR_14("1014", "参数格式错误"),
    ERROR_15("1015", "删除失败"),
    ERROR_16("1016", "信息已存在"),
    ERROR_17("1017", "统计信息有误，请重试"),
    ERROR_18("1018", "参数不合法"),
    ERROR_19("1019", ""),
    ERROR_20("1020", "接口调用不匹配错误"),
    ERROR_21("1021", "电子围栏不存在");


    private String resultCode;
    private String resultDes;

    /**
     * @param resultCode 返回码
     * @param resultDes  返回信息
     */
    ResultCodeEnum(String resultCode, String resultDes) {
        this.resultCode = resultCode;
        this.resultDes = resultDes;
    }

    public String getCode() {
        return resultCode;
    }

    public String getDes() {
        return resultDes;
    }
}
