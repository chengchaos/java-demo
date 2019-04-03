package cn.chengchaos;

import java.io.Serializable;
import java.util.StringJoiner;

public class SetRequest implements Serializable {

    private final String key;
    private final Object value;

    public SetRequest(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SetRequest.class.getSimpleName() + "[", "]")
                .add("key='" + key + "'")
                .add("value=" + value)
                .toString();
    }
}
