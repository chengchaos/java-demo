package cn.chengchaos;

import java.io.Serializable;
import java.util.StringJoiner;

public class GetRequest implements Serializable {

    private final String key;

    public GetRequest(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GetRequest.class.getSimpleName() + "[", "]")
                .add("key='" + key + "'")
                .toString();
    }
}
