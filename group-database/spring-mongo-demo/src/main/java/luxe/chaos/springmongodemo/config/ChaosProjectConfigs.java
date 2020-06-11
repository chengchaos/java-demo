package luxe.chaos.springmongodemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * Such as title ...
 * </p>
 *
 * @author chengchao - 2020/6/1 21:55 <br />
 * @see [相关类方法]
 * @since [产品模块版本]
 */
@Configuration
public class ChaosProjectConfigs {




    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory factory) {

        DbRefResolver resolver = new DefaultDbRefResolver(factory);
        MongoMappingContext mappingContext= new MongoMappingContext();

        MappingMongoConverter mappingMongoConverter =
                new MappingMongoConverter(resolver, mappingContext);

        mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));

        return mappingMongoConverter;
    }

}
