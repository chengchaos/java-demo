package vip.chengchao.lsn004

import org.apache.flink.api.scala.ExecutionEnvironment

/**
  * <p>
  * <strong>
  * 用一句话描述功能
  * </strong><br /><br />
  * 如题。
  * </p>
  *
  * @author chengchaos[as]Administrator - 2019/5/27 0027 下午 5:52 <br />
  * @see 【相关类方法】
  * @since 1.1.0
  */
object DataSetDataSourceApp {

  def main(args: Array[String]): Unit = {

    val env = ExecutionEnvironment
      .getExecutionEnvironment

    fromCollection(env)

  }

  def fromCollection(env: ExecutionEnvironment) : Unit = {

    import org.apache.flink.api.scala._

    val data = 1 to 10
    env.fromCollection(data).print()

  }
}