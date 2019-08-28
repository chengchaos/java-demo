package com.example.myscala.sb

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
  }

}
