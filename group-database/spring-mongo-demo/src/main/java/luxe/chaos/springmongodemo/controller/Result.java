package luxe.chaos.springmongodemo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author chengchao - 2020/6/5 16:33 <br />
 * @see [相关类方法]
 * @since [产品模块版本]
 */
public class Result<T> {

    private String status;
    private String code;
    private String message;
    private T data;

    public static <T> Result<T> successWithData(T data) {
        Result<T> result = new Result<>();
        result.setStatus("SUCCESS");
        result.setCode("10000");
        result.setData(data);

        return result;
    }

    public static Result<Void> success() {
        Result<Void> result = new Result<>();
        result.setStatus("SUCCESS");
        result.setCode("10000");

        return result;
    }

    public static Result<String> error(String code, String data) {
        Result<String> result = new Result<>();
        result.setCode(code);
        result.setData(data);

        return result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String toJsonString() {
        return JSON.toJSONString(this, SerializerFeature.NotWriteDefaultValue);
    }

}

