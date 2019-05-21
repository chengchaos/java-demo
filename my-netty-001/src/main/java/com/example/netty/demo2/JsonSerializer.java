package com.example.netty.demo2;

import com.alibaba.fastjson.JSON;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/15 0015 下午 2:03 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class JsonSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> classType, byte[] bytes) {
        return JSON.parseObject(bytes, classType);
    }
}
