package cn.chengchaos.hbase.hos;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/6/26 18:50 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class HosObjectSummary implements Comparable<HosObjectSummary>, Serializable {

    private String id;
    private String key;
    private String name;
    private long length;
    private String mediaType;
    private long lastModifyTime;
    private String bucket;
    private Map<String, String> attrs;

    public String getContentEncoding() {
        return attrs != null
                ? attrs.get("content-encoding")
                : null;
    }

    @Override
    public int compareTo(HosObjectSummary that) {
        return this.key.compareTo(that.key);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public long getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(long lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public Map<String, String> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<String, String> attrs) {
        this.attrs = attrs;
    }
}
