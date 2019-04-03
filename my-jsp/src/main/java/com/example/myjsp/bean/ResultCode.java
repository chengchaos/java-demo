package com.example.myjsp.bean;

public enum ResultCode {

    SUCCESS("100000", "成功"),
    INTERNAL_CIRCUIT_BREAKE_ERROR("900001", "远程调用被熔断");

    private String code;
    private String msg;
    private String msgCN;

    private ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ResultCode(String code, String msg, String msgCN) {
        this.code = code;
        this.msg = msg;
        this.msgCN = msgCN;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgCN() {
        return this.msgCN;
    }

    public void setMsgCN(String msgCN) {
        this.msgCN = msgCN;
    }
}
