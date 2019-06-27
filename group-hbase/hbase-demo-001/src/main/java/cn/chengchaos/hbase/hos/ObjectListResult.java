package cn.chengchaos.hbase.hos;

import java.util.List;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/6/26 19:01 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class ObjectListResult {

    private String bucket;
    private String maxKey;
    private String minKey;
    private String nextMarker;

    private int maxKeyNumber;
    private int objectCount;

    private String listId;

    private List<HosObjectSummary> hosObjectSummaries;

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getMaxKey() {
        return maxKey;
    }

    public void setMaxKey(String maxKey) {
        this.maxKey = maxKey;
    }

    public String getMinKey() {
        return minKey;
    }

    public void setMinKey(String minKey) {
        this.minKey = minKey;
    }

    public String getNextMarker() {
        return nextMarker;
    }

    public void setNextMarker(String nextMarker) {
        this.nextMarker = nextMarker;
    }

    public int getMaxKeyNumber() {
        return maxKeyNumber;
    }

    public void setMaxKeyNumber(int maxKeyNumber) {
        this.maxKeyNumber = maxKeyNumber;
    }

    public int getObjectCount() {
        return objectCount;
    }

    public void setObjectCount(int objectCount) {
        this.objectCount = objectCount;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public List<HosObjectSummary> getHosObjectSummaries() {
        return hosObjectSummaries;
    }

    public void setHosObjectSummaries(List<HosObjectSummary> hosObjectSummaries) {
        this.hosObjectSummaries = hosObjectSummaries;
    }
}
