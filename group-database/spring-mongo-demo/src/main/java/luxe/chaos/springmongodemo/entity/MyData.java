package luxe.chaos.springmongodemo.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author chengchao - 2020/6/2 13:00 <br />
 * @see [相关类方法]
 * @since [产品模块版本]
 */
@Document("my_data")
public class MyData implements Serializable {

    /**
     * 序列化 ID
     */
    protected static final long serialVersionUID = 1L;

    @Field("_id")
    private String id;

    @Field("vin")
    private String vin;

    @Field("upload_time")
    private long uploadTime;

    @Field("info")
    private String info;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(long uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "MyData{" +
                "id='" + id + '\'' +
                ", vin='" + vin + '\'' +
                ", uploadTime=" + uploadTime +
                ", info='" + info + '\'' +
                '}';
    }
}
