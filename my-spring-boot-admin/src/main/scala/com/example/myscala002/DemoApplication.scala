package com.example.myscala002

//import de.codecentric.boot.admin.server.config.EnableAdminServer
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.{EnableAutoConfiguration, SpringBootApplication}
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
//@EnableEurekaClient
@EnableDiscoveryClient
@EnableAutoConfiguration
//@EnableAdminServer
class DemoApplication

object DemoApplication {


  def main(args: Array[String]): Unit = {

    SpringApplication.run(classOf[DemoApplication], args: _*)
//    new Test2Consumer().run()
  }

}
