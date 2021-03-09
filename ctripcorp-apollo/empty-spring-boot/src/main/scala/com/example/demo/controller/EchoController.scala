package com.example.demo.controller

import org.springframework.web.bind.annotation.{GetMapping, RestController}

@RestController
class EchoController {


  @GetMapping(value = Array("/echo"))
  def echo(name: String = "world") : String = {
    s"hello $name"
  }


}
