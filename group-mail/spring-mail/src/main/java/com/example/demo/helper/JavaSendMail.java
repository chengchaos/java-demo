package com.example.demo.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Collections;
import java.util.Map;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 3/5/2021 2:55 PM <br />
 * @see [相关类]
 * @since 1.0
 */
public class JavaSendMail {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaSendMail.class);

    public static Map<String, Object> sender1(JavaMailSender javaMailSender) {

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
