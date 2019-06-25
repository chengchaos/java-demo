package cn.futuremove.tsp.tbox.controller

import java.util.function.Supplier

import org.springframework.web.bind.annotation.{GetMapping, RestController}
import reactor.core.publisher.Mono

@RestController
class EchoController {


  @GetMapping(value = Array("/echo"))
  def echo(name: String = "world") : Mono[String] = {

    Mono.fromSupplier(new Supplier[String] {
      override def get(): String = "hello " + Option(name).getOrElse("world")
    })
  }


}
