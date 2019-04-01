package com.example.myjsp.bean;

import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Function;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/3/29 0029 下午 4:16 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class Result<T> {

    private String code;
    private String message;
    private String result;
    private T data;

    public static <T> Result<T> success(T t) {
        return new Result(ResultCode.SUCCESS, t);
    }

    public static <T> Result<T> success() {
        return new Result(ResultCode.SUCCESS);
    }

    public static Result fail(ResultCode resultCode) {
        return new Result(resultCode);
    }

    public static Result fail(ResultCode resultcode, Function<String, Optional<String>> replaceSupplier) {
        Result result = fail(resultcode);
        String toast = (String) ((Optional) replaceSupplier.apply(result.getCode())).orElse(result.getResult());
        result.setResult(toast);
        return result;
    }

    public static Result fail(String code, String message) {
        return new Result(code, message);
    }

    public static Result fail(String code, String message, String result) {
        Result res = new Result();
        res.setCode(code);
        res.setMessage(message);
        res.setResult(result);
        return res;
    }

    public Result() {
    }

    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMsg();
    }

    public Result(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMsg();
        this.data = data;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", Result.class.getSimpleName() + "[", "]")
                .add("code='" + code + "'")
                .add("message='" + message + "'")
                .add("result='" + result + "'")
                .add("data=" + data)
                .toString();
    }
}
