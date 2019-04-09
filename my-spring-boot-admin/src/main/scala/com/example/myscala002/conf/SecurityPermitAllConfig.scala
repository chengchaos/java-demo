package com.example.myscala002.conf

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {

  @throws[Exception]
  override protected def configure(http: HttpSecurity): Unit = {
    http.authorizeRequests.anyRequest.permitAll.and.csrf.disable
  }
}
