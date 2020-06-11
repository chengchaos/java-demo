package luxe.chaos.springmongodemo.repos;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import luxe.chaos.springmongodemo.entity.User;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.CollectionCallback;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author chengchao - 2020/5/23 17:20 <br />
 * @see [相关类方法]
 * @since [产品模块版本]
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);


    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void saveUser(User user) {
        this.mongoTemplate.save(user);
    }

    @Override
    public User findByName(String name) {
        // Query query = Query.query(Criteria.where("userName").is(name));
        Criteria criteria = Criteria.where("userName")
                .regex("^" + name + "$", "i");
        Query query = Query.query(criteria);
        return this.mongoTemplate.findOne(query, User.class);
    }

    @Override
    public Optional<Document> findById(String id) {

        return this.mongoTemplate.execute("user", (CollectionCallback<Optional<Document>>) collection -> {
//            Bson bson = new Document("_id", id);
//            ObjectId _id = new ObjectId(id);
            Bson bson = Filters.eq("_id", id);

            FindIterable<Document> documents = collection.find(bson);

            MongoCursor<Document> iterator = documents.iterator();
            if (iterator.hasNext()) {
                Document next = iterator.next();
                LOGGER.info("next => {}", next);
                return Optional.of(next);
            }
            return Optional.empty();
        });
    }

    @Override
    public long update(User user) {
        Query query = Query.query(Criteria.where("id").is(user.getId()));
        Update update = new Update()
                .set("userName", user.getUserName())
                .set("password", user.getPassword())
                ;
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, User.class);

        if (updateResult != null) {
            return updateResult.getMatchedCount();
        }
        return 0;
    }

    @Override
    public void delete(String userId) {
        Query query = Query.query(Criteria.where("id").is(userId));
        this.mongoTemplate.remove(query, User.class);

    }
}
