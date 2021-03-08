package com.example.demo.helper;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 3/5/2021 3:09 PM <br />
 * @see [相关类]
 * @since 1.0
 */
public class NonSprintBootMail1 {


    // 邮件发送协议
    private final static String PROTOCOL = "smtp";

    // SMTP邮件服务器
    private final static String HOST = "smtp.163.com";

    // SMTP邮件服务器默认端口
    private final static String PORT = "25";

    // 是否要求身份认证
    private final static String IS_AUTH = "true";

    // 是否启用调试模式（启用调试模式可打印客户端与服务器交互过程时一问一答的响应消息）
    private final static String IS_ENABLED_DEBUG_MOD = "true";

    // 发件人
    private static String from = "linkingl@163.com";

    // 收件人
    private static String to = "chengchaos@outlook.com";

    private static String senduserName="linkingl@163.com";
    private static String senduserPwd="1q2w3e4r";

    // 初始化连接邮件服务器的会话信息
    private static Properties props = null;

    static {
        props = new Properties();
//        props.setProperty("mail.enable", "true");
        props.setProperty("mail.transport.protocol", PROTOCOL);
        props.setProperty("mail.smtp.host", HOST);
        props.setProperty("mail.smtp.port", PORT);
        props.setProperty("mail.smtp.auth", IS_AUTH);//视情况而定
        props.setProperty("mail.debug",IS_ENABLED_DEBUG_MOD);
    }

    /**
     * 发送简单的文本邮件
     */
    public static boolean sendTextEmail(String to, int code) throws Exception {
        try {
            // 创建Session实例对象
            Session session1 = Session.getDefaultInstance(props);

            // 创建MimeMessage实例对象
            MimeMessage message = new MimeMessage(session1);
            // 设置发件人
            message.setFrom(new InternetAddress(from));
            // 设置邮件主题
            message.setSubject("内燃机注册验证码");
            // 设置收件人
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
            // 设置发送时间
            message.setSentDate(new Date());
            // 设置纯文本内容为邮件正文
            message.setText("您的验证码是："+code+"!验证码有效期是10分钟，过期后请重新获取！"
                    + "中国内燃机学会");
            // 保存并生成最终的邮件内容
            message.saveChanges();

            // 获得Transport实例对象
            Transport transport = session1.getTransport();
            // 打开连接
            transport.connect(senduserName, senduserPwd);
            // 将message对象传递给transport对象，将邮件发送出去
            transport.sendMessage(message, message.getAllRecipients());
            // 关闭连接
            transport.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
