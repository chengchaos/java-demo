package luxe.chaos.springmongodemo.cachedemo;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.cache.jcache.JCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author chengchao - 2020/5/25 18:15 <br />
 * @see [相关类方法]
 * @since [产品模块版本]
 */
//@Configuration
// 标注启动了缓存
//@EnableCaching
public class CacheConfiguration {

    /*
     * ehcache 主要的管理器
     */
    @Bean
    public CacheManager cacheManager() {
        System.out.println("================================");
        JCacheManagerFactoryBean factoryBean = new JCacheManagerFactoryBean();
        if (factoryBean.getObject() != null) {
            return new JCacheCacheManager(factoryBean.getObject());
        }
        throw new IllegalStateException("factory bean is null!!!");
    }

    /*
     * 据shared与否的设置,Spring分别通过CacheManager.create()或new CacheManager()方式来创建一个ehcache基地.
     */
    public JCacheManagerFactoryBean ehCacheManagerFactoryBean() throws URISyntaxException {
        JCacheManagerFactoryBean factoryBean = new JCacheManagerFactoryBean();
        factoryBean.setCacheManagerUri(new URI("classpath:ehcache.xml"));
        return factoryBean;
    }

}
