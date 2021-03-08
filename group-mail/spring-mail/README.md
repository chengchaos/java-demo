

参考：

- [https://www.cnblogs.com/xibei666/p/9016593.html](https://www.cnblogs.com/xibei666/p/9016593.html)

## Spring Boot下发送电子邮件

### Maven包依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```


### Spring Boot的配置

```properties
spring.mail.host=smtp.servie.com
spring.mail.username=用户名  //发送方的邮箱
spring.mail.password=密码  //对于qq邮箱而言 密码指的就是发送方的授权码
spring.mail.port=465

spring.mail.protocol=smtp
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.ssl.enable=true
#是否用启用加密传送的协议验证项
#注意：在spring.mail.password处的值是需要在邮箱设置里面生成的授权码，这个不是真实的密码。
```

## 非Spring Boot下发送电子邮件

### Maven包依赖


```xml
<dependencies>
    <dependency>
        <groupId>com.sun.mail</groupId>
        <artifactId>javax.mail</artifactId>
        <version>1.5.2</version>
    </dependency>
</dependencies>
```

示例代码：

```java

package com.justin.framework.core.utils.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * 使用SMTP协议发送电子邮件
 */
public class sendEmailCode {


    // 邮件发送协议
    private final static String PROTOCOL = "smtp";

    // SMTP邮件服务器
    private final static String HOST = "mail.tdb.com";

    // SMTP邮件服务器默认端口
    private final static String PORT = "25";

    // 是否要求身份认证
    private final static String IS_AUTH = "true";

    // 是否启用调试模式（启用调试模式可打印客户端与服务器交互过程时一问一答的响应消息）
    private final static String IS_ENABLED_DEBUG_MOD = "true";

    // 发件人
    private static String from = "tdbjrcrm@tdb.com";

    // 收件人
    private static String to = "db_yangruirui@tdbcwgs.com";

    private static String senduserName="tdbjrcrm@tdb.com";
    private static String senduserPwd="New*2016";

    // 初始化连接邮件服务器的会话信息
    private static Properties props = null;

    static {
        props = new Properties();
        props.setProperty("mail.enable", "true");
        props.setProperty("mail.transport.protocol", PROTOCOL);
        props.setProperty("mail.smtp.host", HOST);
        props.setProperty("mail.smtp.port", PORT);
        props.setProperty("mail.smtp.auth", IS_AUTH);//视情况而定
        props.setProperty("mail.debug",IS_ENABLED_DEBUG_MOD);
    }


    /**
     * 发送简单的文本邮件
     */
    public static boolean sendTextEmail(String to,int code) throws Exception {
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
            message.setRecipient(RecipientType.TO, new InternetAddress(to));
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
            transport.connect("meijiajiang2016", "");
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

    public static void main(String[] args) throws Exception {
        sendHtmlEmail("db_yangruirui@tdbcwgs.com", 88888);
    }

    /**
     * 发送简单的html邮件
     */
    public static boolean sendHtmlEmail(String to,int code) throws Exception {
        // 创建Session实例对象
        Session session1 = Session.getInstance(props, new MyAuthenticator());

        // 创建MimeMessage实例对象
        MimeMessage message = new MimeMessage(session1);
        // 设置邮件主题
        message.setSubject("内燃机注册");
        // 设置发送人
        message.setFrom(new InternetAddress(from));
        // 设置发送时间
        message.setSentDate(new Date());
        // 设置收件人
        message.setRecipients(RecipientType.TO, InternetAddress.parse(to));
        // 设置html内容为邮件正文，指定MIME类型为text/html类型，并指定字符编码为gbk
        message.setContent("<div style='width: 600px;margin: 0 auto'><h3 style='color:#003E64; text-align:center; '>内燃机注册验证码</h3><p style=''>尊敬的用户您好：</p><p style='text-indent: 2em'>您在注册内燃机账号，此次的验证码是："+code+",有效期10分钟!如果过期请重新获取。</p><p style='text-align: right; color:#003E64; font-size: 20px;'>中国内燃机学会</p></div>","text/html;charset=utf-8");

        //设置自定义发件人昵称
        String nick="";
        try {
            nick=javax.mail.internet.MimeUtility.encodeText("中国内燃机学会");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        message.setFrom(new InternetAddress(nick+" <"+from+">"));
        // 保存并生成最终的邮件内容
        message.saveChanges();

        // 发送邮件
        try {
            Transport.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 发送带内嵌图片的HTML邮件
     */
    public static void sendHtmlWithInnerImageEmail() throws MessagingException {
        // 创建Session实例对象
        Session session = Session.getDefaultInstance(props, new MyAuthenticator());

        // 创建邮件内容
        MimeMessage message = new MimeMessage(session);
        // 邮件主题,并指定编码格式
        message.setSubject("带内嵌图片的HTML邮件", "utf-8");
        // 发件人
        message.setFrom(new InternetAddress(from));
        // 收件人
        message.setRecipients(RecipientType.TO, InternetAddress.parse(to));
        // 抄送
        message.setRecipient(RecipientType.CC, new InternetAddress("java_test@sohu.com"));
        // 密送 (不会在邮件收件人名单中显示出来)
        message.setRecipient(RecipientType.BCC, new InternetAddress("417067629@qq.com"));
        // 发送时间
        message.setSentDate(new Date());

        // 创建一个MIME子类型为“related”的MimeMultipart对象
        MimeMultipart mp = new MimeMultipart("related");
        // 创建一个表示正文的MimeBodyPart对象，并将它加入到前面创建的MimeMultipart对象中
        MimeBodyPart htmlPart = new MimeBodyPart();
        mp.addBodyPart(htmlPart);
        // 创建一个表示图片资源的MimeBodyPart对象，将将它加入到前面创建的MimeMultipart对象中
        MimeBodyPart imagePart = new MimeBodyPart();
        mp.addBodyPart(imagePart);

        // 将MimeMultipart对象设置为整个邮件的内容
        message.setContent(mp);

        // 设置内嵌图片邮件体
        DataSource ds = new FileDataSource(new File("resource/firefoxlogo.png"));
        DataHandler dh = new DataHandler(ds);
        imagePart.setDataHandler(dh);
        imagePart.setContentID("firefoxlogo.png");  // 设置内容编号,用于其它邮件体引用

        // 创建一个MIME子类型为"alternative"的MimeMultipart对象，并作为前面创建的htmlPart对象的邮件内容
        MimeMultipart htmlMultipart = new MimeMultipart("alternative");
        // 创建一个表示html正文的MimeBodyPart对象
        MimeBodyPart htmlBodypart = new MimeBodyPart();
        // 其中cid=androidlogo.gif是引用邮件内部的图片，即imagePart.setContentID("androidlogo.gif");方法所保存的图片
        htmlBodypart.setContent("<span style='color:red;'>这是带内嵌图片的HTML邮件哦！！！<img src=\"cid:firefoxlogo.png\" /></span>","text/html;charset=utf-8");
        htmlMultipart.addBodyPart(htmlBodypart);
        htmlPart.setContent(htmlMultipart);

        // 保存并生成最终的邮件内容
        message.saveChanges();

        // 发送邮件
        Transport.send(message);
    }

    /**
     * 发送带内嵌图片、附件、多收件人(显示邮箱姓名)、邮件优先级、阅读回执的完整的HTML邮件
     */
    public static void sendMultipleEmail() throws Exception {
        String charset = "utf-8";   // 指定中文编码格式
        // 创建Session实例对象
        Session session = Session.getInstance(props,new MyAuthenticator());

        // 创建MimeMessage实例对象
        MimeMessage message = new MimeMessage(session);
        // 设置主题
        message.setSubject("使用JavaMail发送混合组合类型的邮件测试");
        // 设置发送人
        message.setFrom(new InternetAddress(from,"新浪测试邮箱",charset));
        // 设置收件人
        message.setRecipients(RecipientType.TO,
                new Address[] {
                        // 参数1：邮箱地址，参数2：姓名（在客户端收件只显示姓名，而不显示邮件地址），参数3：姓名中文字符串编码
                        new InternetAddress("java_test@sohu.com", "张三_sohu", charset),
                        new InternetAddress("xyang0917@163.com", "李四_163", charset),
                }
        );
        // 设置抄送
        message.setRecipient(RecipientType.CC, new InternetAddress("xyang0917@gmail.com","王五_gmail",charset));
        // 设置密送
        message.setRecipient(RecipientType.BCC, new InternetAddress("xyang0917@qq.com", "赵六_QQ", charset));
        // 设置发送时间
        message.setSentDate(new Date());
        // 设置回复人(收件人回复此邮件时,默认收件人)
        message.setReplyTo(InternetAddress.parse("\"" + MimeUtility.encodeText("田七") + "\" <417067629@qq.com>"));
        // 设置优先级(1:紧急   3:普通    5:低)
        message.setHeader("X-Priority", "1");
        // 要求阅读回执(收件人阅读邮件时会提示回复发件人,表明邮件已收到,并已阅读)
        message.setHeader("Disposition-Notification-To", from);

        // 创建一个MIME子类型为"mixed"的MimeMultipart对象，表示这是一封混合组合类型的邮件
        MimeMultipart mailContent = new MimeMultipart("mixed");
        message.setContent(mailContent);

        // 附件
        MimeBodyPart attach1 = new MimeBodyPart();
        MimeBodyPart attach2 = new MimeBodyPart();
        // 内容
        MimeBodyPart mailBody = new MimeBodyPart();

        // 将附件和内容添加到邮件当中
        mailContent.addBodyPart(attach1);
        mailContent.addBodyPart(attach2);
        mailContent.addBodyPart(mailBody);

        // 附件1(利用jaf框架读取数据源生成邮件体)
        DataSource ds1 = new FileDataSource("resource/Earth.bmp");
        DataHandler dh1 = new DataHandler(ds1);
        attach1.setFileName(MimeUtility.encodeText("Earth.bmp"));
        attach1.setDataHandler(dh1);

        // 附件2
        DataSource ds2 = new FileDataSource("resource/如何学好C语言.txt");
        DataHandler dh2 = new DataHandler(ds2);
        attach2.setDataHandler(dh2);
        attach2.setFileName(MimeUtility.encodeText("如何学好C语言.txt"));

        // 邮件正文(内嵌图片+html文本)
        MimeMultipart body = new MimeMultipart("related");  //邮件正文也是一个组合体,需要指明组合关系
        mailBody.setContent(body);

        // 邮件正文由html和图片构成
        MimeBodyPart imgPart = new MimeBodyPart();
        MimeBodyPart htmlPart = new MimeBodyPart();
        body.addBodyPart(imgPart);
        body.addBodyPart(htmlPart);

        // 正文图片
        DataSource ds3 = new FileDataSource("resource/firefoxlogo.png");
        DataHandler dh3 = new DataHandler(ds3);
        imgPart.setDataHandler(dh3);
        imgPart.setContentID("firefoxlogo.png");

        // html邮件内容
        MimeMultipart htmlMultipart = new MimeMultipart("alternative");
        htmlPart.setContent(htmlMultipart);
        MimeBodyPart htmlContent = new MimeBodyPart();
        htmlContent.setContent(
                "<span style='color:red'>这是我自己用java mail发送的邮件哦！" +
                        "<img src='cid:firefoxlogo.png' /></span>"
                , "text/html;charset=gbk");
        htmlMultipart.addBodyPart(htmlContent);

        // 保存邮件内容修改
        message.saveChanges();

        /*File eml = buildEmlFile(message);
        sendMailForEml(eml);*/

        // 发送邮件
        Transport.send(message);
    }

    /**
     * 将邮件内容生成eml文件
     * @param message 邮件内容
     */
    public static File buildEmlFile(Message message) throws MessagingException, FileNotFoundException, IOException {
        File file = new File("c:\\" + MimeUtility.decodeText(message.getSubject())+".eml");
        message.writeTo(new FileOutputStream(file));
        return file;
    }

    /**
     * 发送本地已经生成好的email文件
     */
    public static void sendMailForEml(File eml) throws Exception {
        // 获得邮件会话
        Session session = Session.getInstance(props,new MyAuthenticator());
        // 获得邮件内容,即发生前生成的eml文件
        InputStream is = new FileInputStream(eml);
        MimeMessage message = new MimeMessage(session,is);
        //发送邮件
        Transport.send(message);
    }

    /**
     * 向邮件服务器提交认证信息
     */
    static class MyAuthenticator extends Authenticator {

        private String username = "";

        private String password = "";

        public MyAuthenticator() {
            super();
            this.password=senduserPwd;
            this.username=senduserName;
        }

        public MyAuthenticator(String username, String password) {
            super();
            this.username = username;
            this.password = password;
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {

            return new PasswordAuthentication(username, password);
        }
    }
}
```

Demo 2 

```java
package com.justin.framework.core.utils.email;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
public class MailManagerUtils {
    //发送邮件
    public static boolean sendMail(Email email) {
        String subject = email.getSubject();
        String content = email.getContent();
        String[] recievers = email.getRecievers();
        String[] copyto = email.getCopyto();
        String attbody = email.getAttbody();
        String[] attbodys = email.getAttbodys();
        if(recievers == null || recievers.length <=0) {
            return false;
        }
        try {
            Properties props =new Properties();
            props.setProperty("mail.enable", "true");
            props.setProperty("mail.protocal", "smtp");
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.user", "tdbjrcrm@tdb.com");
            props.setProperty("mail.pass", "New***");
            props.setProperty("mail.smtp.host","mail.tdb.com");

            props.setProperty("mail.smtp.from","tdbjrcrm@tdb.com");
            props.setProperty("mail.smtp.fromname","tdbVC");

            // 创建一个程序与邮件服务器的通信
            Session mailConnection = Session.getInstance(props, null);
            Message msg = new MimeMessage(mailConnection);

            // 设置发送人和接受人
            Address sender = new InternetAddress(props.getProperty("mail.smtp.from"));
            // 多个接收人
            msg.setFrom(sender);

            Set<InternetAddress> toUserSet = new HashSet<InternetAddress>();
            // 邮箱有效性较验
            for (int i = 0; i < recievers.length; i++) {
                if (recievers[i].trim().matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)+$")) {
                    toUserSet.add(new InternetAddress(recievers[i].trim()));
                }
            }
            msg.setRecipients(Message.RecipientType.TO, toUserSet.toArray(new InternetAddress[0]));
            // 设置抄送
            if (copyto != null) {
                Set<InternetAddress> copyToUserSet = new HashSet<InternetAddress>();
                // 邮箱有效性较验
                for (int i = 0; i < copyto.length; i++) {
                    if (copyto[i].trim().matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)+$")) {
                        copyToUserSet.add(new InternetAddress(copyto[i].trim()));
                    }
                }
                //    msg.setRecipients(Message.RecipientType.CC,(Address[])InternetAddress.parse(copyto));
                msg.setRecipients(Message.RecipientType.CC, copyToUserSet.toArray(new InternetAddress[0]));
            }
            // 设置邮件主题
            msg.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B")); // 中文乱码问题

            // 设置邮件内容
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(content, "text/html; charset=UTF-8"); // 中文
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            msg.setContent(multipart);

            /********************** 发送附件 ************************/
            if (attbody != null) {
                String[] filePath = attbody.split(";");
                for (String filepath : filePath) {
                    //设置信件的附件(用本地机上的文件作为附件)
                    BodyPart mdp = new MimeBodyPart();
                    FileDataSource fds = new FileDataSource(filepath);
                    DataHandler dh = new DataHandler(fds);
                    mdp.setFileName(MimeUtility.encodeText(fds.getName()));
                    mdp.setDataHandler(dh);
                    multipart.addBodyPart(mdp);
                }
                //把mtp作为消息对象的内容
                msg.setContent(multipart);
            };
            if (attbodys != null) {
                for (String filepath : attbodys) {
                    //设置信件的附件(用本地机上的文件作为附件)
                    BodyPart mdp = new MimeBodyPart();
                    FileDataSource fds = new FileDataSource(filepath);
                    DataHandler dh = new DataHandler(fds);
                    mdp.setFileName(MimeUtility.encodeText(fds.getName()));
                    mdp.setDataHandler(dh);
                    multipart.addBodyPart(mdp);
                }
                //把mtp作为消息对象的内容
                msg.setContent(multipart);
            }
            ;
            /********************** 发送附件结束 ************************/

            // 先进行存储邮件
            msg.saveChanges();
            System.out.println("正在发送邮件....");
            Transport trans = mailConnection.getTransport(props.getProperty("mail.protocal"));
            // 邮件服务器名,用户名，密码
            trans.connect(props.getProperty("mail.smtp.host"), props.getProperty("mail.user"), props.getProperty("mail.pass"));
            trans.sendMessage(msg, msg.getAllRecipients());
            System.out.println("发送邮件成功！");

            // 关闭通道
            if (trans.isConnected()) {
                trans.close();
            }
            return true;
        } catch (Exception e) {
            System.err.println("邮件发送失败！" + e);
            return false;
        } finally {
        }
    }

    // 发信人，收信人，回执人邮件中有中文处理乱码,res为获取的地址
    // http默认的编码方式为ISO8859_1
    // 对含有中文的发送地址，使用MimeUtility.decodeTex方法
    // 对其他则把地址从ISO8859_1编码转换成gbk编码
    public static String getChineseFrom(String res) {
        String from = res;
        try {
            if (from.startsWith("=?GB") || from.startsWith("=?gb") || from.startsWith("=?UTF")) {
                from = MimeUtility.decodeText(from);
            } else {
                from = new String(from.getBytes("ISO8859_1"), "GBK");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return from;
    }

    // 转换为GBK编码
    public static String toChinese(String strvalue) {
        try {
            if (strvalue == null)
                return null;
            else {
                strvalue = new String(strvalue.getBytes("ISO8859_1"), "GBK");
                return strvalue;
            }
        } catch (Exception e) {
            return null;
        }
    }
    public static void main(String[] args) {
        Email email=new Email();
        email.setRecievers(new String[]{"db_yangruirui@tdbcwgs.com"});
        email.setSubject("TEST测件");
        email.setContent("TEST测试");
        sendMail(email);
    }

}
````