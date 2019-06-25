package cn.futuremove.tsp.tbox.dao.mongo

import cn.futuremove.tsp.kafka.consumer.KafkaConsumerApp
import org.junit.{Assert, Test}
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.test.context.junit4.SpringRunner

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/6/10 15:31 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
@RunWith(classOf[SpringRunner])
@SpringBootTest(classes = Array(classOf[KafkaConsumerApp]),
  webEnvironment = WebEnvironment.RANDOM_PORT )
class MongoClient1Test {

  def test() : Unit = {
    Assert.assertTrue("hello", true)
  }
}
