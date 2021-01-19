package cn.springcloud.book.entities;

import java.util.List;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 * </p>
 *
 * @author Cheng, Chao - 1/13/2021 5:06 PM <br />
 * @see [相关类]
 * @since 1.0
 */
public class DataWrapper<T> {

    private int code;
    private String info;

    private T data;

    public static <T> DataWrapper<T> wrapCustomer(int code,
                                                  String info,
                                                  T data) {
        DataWrapper<T> dw = new DataWrapper<>();
        dw.setCode(code);
        dw.setInfo(info);
        dw.setData(data);
        return dw;
    }

    public static <A> DataWrapper<List<A>> wrapList(List<A> listData) {
        DataWrapper<List<A>> dw = new DataWrapper<>();
        dw.setCode(1000);
        dw.setInfo("success");

        dw.setData(listData);
        return dw;
    }

    public static DataWrapper<String> wrapError(String errorDetail) {
        DataWrapper<String> dw = new DataWrapper<>();
        dw.setCode(9999);
        dw.setInfo("error");
        dw.setData(errorDetail);
        return dw;
    }

    public static <T> DataWrapper<T> wrapData(T dataDetail) {
        DataWrapper<T> dw = new DataWrapper<>();
        dw.setCode(1000);
        dw.setInfo("success");
        dw.setData(dataDetail);
        return dw;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
