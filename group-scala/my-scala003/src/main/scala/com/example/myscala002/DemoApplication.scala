package com.example.myscala002

import com.example.myscala002.kafka.{AloneConsumer, Test2Consumer}
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
//@EnableEurekaClient
//@EnableDiscoveryClient
class DemoApplication

object DemoApplication {

  def main(args: Array[String]): Unit = {

    SpringApplication.run(classOf[DemoApplication], args: _*)
//    new Test2Consumer().run()
    new AloneConsumer().run();
  }

}
