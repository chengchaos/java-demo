package luxe.chaos.springmongodemo.cachedemo;

import java.io.Serializable;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author chengchao - 2020/5/25 22:17 <br />
 * @see [相关类方法]
 * @since [产品模块版本]
 */
public class ByteArray implements Serializable {

    static final long serialVersionUID = 42L;

    private byte[] arrays;

    public ByteArray() {
        super();
    }

    public ByteArray(byte[] arrays) {
        super();
        this.arrays = arrays;
    }

    public byte[] getArrays() {
        return arrays;
    }

    public void setArrays(byte[] arrays) {
        this.arrays = arrays;
    }
}
