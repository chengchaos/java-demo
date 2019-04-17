package com.example.netty.demo2;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/15 0015 下午 2:01 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public interface Serializer {

    /**
     * Json 序列化
     */
    byte JSON_SERIALIZER = 1;

    Serializer DEFAULT = new JsonSerializer();

    byte getSerializerAlgorithm();

    byte[] serialize(Object object);

    <T> T deserialize(Class<T> classType, byte[] bytes) ;
}
