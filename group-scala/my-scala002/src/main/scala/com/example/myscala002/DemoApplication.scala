package com.example.myscala002

import com.example.myscala002.kafka.Producer
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
//@EnableEurekaClient
//@EnableDiscoveryClient
class DemoApplication

object DemoApplication {

  def main(args: Array[String]): Unit = {

    SpringApplication.run(classOf[DemoApplication], args: _*)

    Producer.flushData(60 * 60)
  }

}
