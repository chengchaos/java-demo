package luxe.chaos.springmongodemo.repos;

import luxe.chaos.springmongodemo.entity.MyData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author chengchao - 2020/6/2 13:07 <br />
 * @see [相关类方法]
 * @since [产品模块版本]
 */
@Repository
public class MyDataRepositoryImpl implements MyDataRepository {

    public static final Logger LOGGER = LoggerFactory.getLogger(MyDataRepositoryImpl.class);


    private final MongoTemplate mongoTemplate;

    @Autowired
    public MyDataRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public String insert(MyData myData) {
        MyData insert = this.mongoTemplate.insert(myData);

        return "OK";

    }

    @Override
    public String insertMore(List<MyData> myDataList) {
        Collection<MyData> myData = this.mongoTemplate.insertAll(myDataList);

        return "OK";
    }

    @Override
    public MyData getById(String id) {

        Criteria criteria = Criteria.where("_id").is(id);
        Query query = Query.query(criteria);

        return this.mongoTemplate.findOne(query, MyData.class);
    }
}
