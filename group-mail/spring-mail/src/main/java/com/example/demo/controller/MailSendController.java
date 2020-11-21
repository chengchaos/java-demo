package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;

@RestController
public class MailSendController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailSendController.class);

    private JavaMailSender javaMailSender;

    @PostConstruct
    public void postConstructForAutoSend() {

//        Map<String, Object> result = this.sendSimpleMail(this.javaMailSender);
//        LOGGER.info("send result => {}", result);
    }

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @GetMapping(value = {"/v1/say-hai"})
    public Map<String, Object> sayHai() {
        LOGGER.debug("perfomr sayHai ...");
        return Collections.singletonMap("message", "hai ~");
    }

    @PostMapping(value = {"/v1/send-mail"})
    public Map<String, Object> sendMail(Map<String, Object> param) {

        LOGGER.debug("param => {}", param);
        return sendSimpleMail(javaMailSender);
    }


    /**
     *
     * @param javaMailSender JavaMailSender
     * @return FAILURE | SUCCESS
     */
    private Map<String, Object> sendSimpleMail(JavaMailSender javaMailSender) {

        LOGGER.info("开始发邮件");

        String mailFrom  = "c.b.cheng@futuremove.cn";
        String mailTo = "chengchaos@outlook.com";
        String mailSubject = "标题：发送Html内容测试邮件";
        String mailContent = "<h1>大标题-h1</h1>" +
                "<p style='color:#F00'>红色字</p>" +
                "<p style='text-align:right'>右对齐</p>" +
                "BR <br />"
                ;

        MimeMessage message;
        try {
            message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailFrom);
            helper.setTo(mailTo);
            helper.setSubject(mailSubject);
            helper.setText(mailContent, true);

//            FileSystemResource fileSystemResource = new FileSystemResource(Paths.get("file:///tmp/invoice.jpg"));
//            helper.addAttachment("电子发票.jpg", fileSystemResource);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            LOGGER.error("MessagingException :( ", e);
            return Collections.singletonMap("FAILURE", e.getCause());
        }

        return Collections.singletonMap("SUCCESS", "OK");
    }
}
