package luxe.chaos.springboot.demo.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/7/2021 2:21 PM <br />
 * @see [相关类]
 * @since 1.0
 */
public final class JacksonHelper {

    private static final Logger logger = LoggerFactory.getLogger(JacksonHelper.class);

    private static final ThreadLocal<ObjectMapper> local = ThreadLocal.withInitial(JacksonHelper::newObjectMapper);

    private JacksonHelper() {
        super();
    }

    public static ObjectMapper newObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        //在反序列化时忽略在 json 中存在但 Java 对象不存在的属性
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                false);
        //在序列化时日期格式默认为 yyyy-MM-dd'T'HH:mm:ss.SSSZ
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        //在序列化时忽略值为 null 的属性
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //忽略值为默认值的属性
        mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_DEFAULT);

        return mapper;
    }

    public static Optional<String> toJson(Object input) {
        ObjectMapper mapper = local.get();
        if (mapper == null) {
            mapper = new ObjectMapper();
        }
        logger.debug("mapper => {}", mapper);
        try {
            //mapper.writerWithDefaultPrettyPrinter()
            return Optional.of(mapper.writeValueAsString(input));
        } catch (JsonProcessingException e) {
            logger.error("", e);
        }

        return Optional.empty();
    }

    public static <T> Optional<T> toObject(String input, Class<T> classType) {
        ObjectMapper mapper = local.get();
        try {
            return Optional.of(mapper.readValue(input, classType));
        } catch (JsonProcessingException e) {
            logger.error("", e);
        }
        return Optional.empty();
    }

    public static <T> Optional<T> toObject(String input, TypeReference<T> typeReference) {
        ObjectMapper mapper = local.get();
        if (mapper == null) {
            mapper = new ObjectMapper();
        }
        try {
            return Optional.of(mapper.readValue(input, typeReference));
        } catch (JsonProcessingException e){
            logger.error("", e);
        }
        return Optional.empty();
    }


    public static void shutdown() {
        local.remove();
    }
}
