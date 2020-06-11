package luxe.chaos.springmongodemo.cachedemo;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author chengchao - 2020/5/25 17:24 <br />
 * @see [相关类方法]
 * @since [产品模块版本]
 */
//@Component
public class EventLogger implements CacheEventListener<String, byte[]> {


    private static final Logger LOGGER = LoggerFactory.getLogger(EventLogger.class);

    @Override
    public void onEvent(CacheEvent<? extends String, ? extends byte[]> cacheEvent) {

        LOGGER.info("Event: {}, Key: {}, old value: {}, new value: {}",
                cacheEvent.getType(),
                cacheEvent.getKey(),
                cacheEvent.getOldValue(),
                cacheEvent.getNewValue());
    }
}
