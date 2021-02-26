package luxe.chaos.threads.demo001

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class DemoApplication

object DemoApplication {

  def main(args: Array[String]) :Unit = {
    SpringApplication.run(classOf[DemoApplication], args: _*)
  }
}