package vip.chengchao.by2020

import org.apache.flink.api.scala.ExecutionEnvironment


object WordCount001 {

  def main(args: Array[String]): Unit = {
    // file
    val file = "C:\\works\\git-repo\\github.com\\chengchaos\\java-demo\\group-flink\\flink-train-scala\\src\\main\\resources\\hello.txt"

    // 1. env
    val env = ExecutionEnvironment.getExecutionEnvironment
    // 2. open file
    val text = env.readTextFile(file)
    text.print()
  }
}
