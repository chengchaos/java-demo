package cn.chengchaos.hbase.hos;

import okhttp3.Response;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/6/26 18:56 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class HosObject {

    private ObjectMetaData metaData;
    private InputStream content;
    private Response response;


    public HosObject() {
        super();
    }

    public HosObject(Response response) {
        this.response = response;
    }

    public void close() {
        try {
            if (response != null) {
                response.close();
            }
            if (content != null) {
                this.content.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
