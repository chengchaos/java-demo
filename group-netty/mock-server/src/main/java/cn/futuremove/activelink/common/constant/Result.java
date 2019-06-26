package cn.futuremove.activelink.common.constant;

/**
 * Created by Littl on 2018/7/11.
 */
public class Result {
    /**
     * 信息码
     */
    private String code;
    /**
     * 标识
     */
    private String flag;
    /**
     * 返回信息
     */
    private String msg;
    /**
     * 返回数据
     */
    private Object data;

    public Result() {
        super();
    }

    public Result(String flag) {
        super();
        this.flag = flag;
    }

    public Result(String flag, String code, String msg) {
        super();
        this.flag = flag;
        this.code = code;
        this.msg = msg;
    }

    public Result(String flag, Object data) {
        super();
        this.flag = flag;
        this.data = data;
    }

    public Result(String code, String flag, String msg, Object data) {
        super();
        this.code = code;
        this.flag = flag;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Result getSuccess() {
        Result result = new Result();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setFlag(Flag.SUCCESS);
        return result;
    }

    public static Result getSuccess(ResultCodeEnum resultCode){
        Result result=new Result();
        result.setCode(resultCode.getCode());
        result.setFlag(resultCode.getDes());
        return result;
    }

    public static Result getSuccess(Object data) {
        Result result = new Result();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setFlag(Flag.SUCCESS);
        result.setData(data);
        return result;
    }

    public static Result getFail(ResultCodeEnum resultCodeEnum) {
        Result result = new Result();
        result.setCode(resultCodeEnum.getCode());
        result.setFlag(Flag.FAIL);
        result.setMsg(resultCodeEnum.getDes());
        return result;
    }

    public static Result getFail(ResultCodeEnum resultCodeEnum, Object data) {
        Result result = getFail(resultCodeEnum);
        result.setData(data);
        return result;
    }

}
