package luxe.chaos.springmongodemo.cachedemo;

import com.esotericsoftware.kryo.kryo5.Kryo;
import com.esotericsoftware.kryo.kryo5.io.Input;
import com.esotericsoftware.kryo.kryo5.io.Output;
import com.esotericsoftware.kryo.kryo5.serializers.FieldSerializer;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheRuntimeConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.event.EventFiring;
import org.ehcache.event.EventOrdering;
import org.ehcache.event.EventType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 * EhCache 已经变了很多。
 * </strong><br /><br />
 * 如题
 * <p>
 * https://www.ehcache.org/
 * </p>
 *
 * @author chengchao - 2020/5/25 19:23 <br />
 * @see [相关类方法]
 * @since [产品模块版本]
 */
public class CacheTest {


    private static final Logger LOGGER = LoggerFactory.getLogger(CacheTest.class);

    private static CacheManager cacheManager;


    private static ThreadLocal<Kryo> kryoThreadLocal;


    @BeforeAll
    public static void init() {

        kryoThreadLocal = new ThreadLocal<Kryo>() {
            @Override
            protected Kryo initialValue() {
                Kryo kryo = new Kryo();
                kryo.setReferences(true);
                kryo.setRegistrationRequired(false);

//                kryo.register(Person.class, new FieldSerializer<>(kryo, Person.class));
                return kryo;
            }
        };


        ResourcePoolsBuilder resourcePoolsBuilder = ResourcePoolsBuilder.heap(100);
        CacheConfigurationBuilder<String, byte[]> config = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, byte[].class, resourcePoolsBuilder)
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(10)));

//        CacheConfiguration<Long, String> cacheConfiguration = CacheConfigurationBuilder
//                .newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(100))
//                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(20)))
//                .build();


        cacheManager = CacheManagerBuilder
                .newCacheManagerBuilder()
                .withCache("preConfigured", config)
                .build(false);


        Set<EventType> set = new HashSet<>(
                Arrays.asList(
                        EventType.EVICTED,
                        EventType.EXPIRED,
                        EventType.REMOVED,
                        EventType.CREATED,
                        EventType.UPDATED));

        cacheManager.init();

        Cache<String, byte[]> cache = cacheManager.getCache("preConfigured",
                String.class, byte[].class);
        CacheRuntimeConfiguration<String, byte[]> cacheRuntimeConfiguration = cache.getRuntimeConfiguration();
        cacheRuntimeConfiguration.registerCacheEventListener(new EventLogger(),
                EventOrdering.ORDERED,
                EventFiring.ASYNCHRONOUS,
                set);

        LOGGER.info("config => {}", config);
        LOGGER.info("cacheRuntimeConfiguration => {}", cacheRuntimeConfiguration);


        LOGGER.info("cache manager init ...........");

    }


    @AfterAll
    public static void close() {
        cacheManager.close();
        LOGGER.info("cache manager close ...........");
    }


    @Test
    public void test() {

//        Cache<Long, String> myCache = cacheManager
//                .createCache("heap-cache", builder.build());

//        Cache<String, ByteArray> cache = cacheManager .getCache("preConfigured", String.class, ByteArray.class);

        Cache<String, byte[]> cache = cacheManager.getCache("preConfigured",
                String.class, byte[].class);


        PersonService ps = new PersonService();

        Person p1 = ps.getPerson(1);

        Output out = new Output(new ByteArrayOutputStream(8192));
        kryoThreadLocal.get().writeObject(out, p1);
        int position = out.position();
        byte[] ba = out.toBytes();
        byte[] buffer = out.getBuffer();

        //System.arraycopy(out.getBuffer(), 0, ba, 0, position);
        long total = out.total();

        LOGGER.info("buffer size => {}, ba size => {}, position => {}, total => {}", buffer.length, ba.length, position, total);

        cache.put("1", ba);

    }

    @Test
    public void test2() throws InterruptedException {

        Cache<String, byte[]> preConfigured = cacheManager.getCache("preConfigured", String.class, byte[].class);

        for (int i = 0; i < 10; i++) {
            byte[] value = preConfigured.get("1");

            if (value != null) {
                Input input = new Input(value);
                Person p = kryoThreadLocal.get().readObject(input, Person.class);
                LOGGER.info("{} value => {}", i, p);
            } else {
                LOGGER.info("{} value => NULL !!!", i);
            }
            TimeUnit.MILLISECONDS.sleep(200L);
        }
    }
}
