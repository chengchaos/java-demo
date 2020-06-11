package luxe.chaos.springmongodemo.repos;

import luxe.chaos.springmongodemo.entity.MyData;

import java.util.List;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author chengchao - 2020/6/2 13:06 <br />
 * @see [相关类方法]
 * @since [产品模块版本]
 */
public interface MyDataRepository {

    String insert(MyData myData);

    String insertMore(List<MyData> myDataList);

    MyData getById(String id);


}
