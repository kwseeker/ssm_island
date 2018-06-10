package top.kwseeker.www.ssm_island.util;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

/**
 * 发送修改密码的邮件工具类
 * 官方网址：
 * https://javaee.github.io/javamail/
 */
public class MailSenderUtil {

//    private MailContentInfo mailContentInfo;
//    private MailAuthenticator mailAuthenticator;

    public boolean sendTextMail(MailContentInfo mailInfo) {
        // 判断是否需要身份认证
        MailAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        if (mailInfo.isValidate()) {
            // 如果需要身份认证，则创建一个密码验证器
            authenticator = new MailAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(pro,authenticator);
        try {
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
            Address from = new InternetAddress(mailInfo.getFromAddress());
            // 设置邮件消息的发送者
            mailMessage.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中
            Address to = new InternetAddress(mailInfo.getToAddress());
            mailMessage.setRecipient(Message.RecipientType.TO,to);
            // 设置邮件消息的主题
            mailMessage.setSubject(mailInfo.getSubject());
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());
            // 设置邮件消息的主要内容
            String mailContent = mailInfo.getContent();
            mailMessage.setText(mailContent);
            // 发送邮件
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean sendHtmlMail(MailContentInfo mailInfo) {
        // 判断是否需要身份认证
        MailAuthenticator authenticator = null;

        Properties pro = mailInfo.getProperties();
        //如果需要身份认证，则创建一个密码验证器
        if (mailInfo.isValidate()) {
            authenticator = new MailAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        sendMailSession.setDebug(true); // 打印调试信息
        try {
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            Address from = new InternetAddress(mailInfo.getFromAddress());  // 创建邮件发送者地址
            mailMessage.setFrom(from);                                      // 设置邮件消息的发送者
            Address to = new InternetAddress(mailInfo.getToAddress());      // 创建邮件的接收者地址，并设置到邮件消息中
            mailMessage.setRecipient(Message.RecipientType.TO,to);          // Message.RecipientType.TO属性表示接收者的类型为TO
            mailMessage.setSubject(mailInfo.getSubject());                  // 设置邮件消息的主题
            mailMessage.setSentDate(new Date());                            // 设置邮件消息发送的时间
            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
            Multipart mainPart = new MimeMultipart();
            BodyPart html = new MimeBodyPart();         // 创建一个包含HTML内容的MimeBodyPart
            html.setContent(mailInfo.getContent(), "text/html; charset=utf-8"); // 设置HTML内容
            mainPart.addBodyPart(html);                 // 将MiniMultipart对象设置为邮件内容
            mailMessage.setContent(mainPart);
            // 发送邮件
//            Transport.send(mailMessage);
            Transport transport = sendMailSession.getTransport();
            transport.connect();
            transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
            transport.close();
            return true;
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    //邮件内容及配置
    public static class MailContentInfo {
        private String mailServerHost;      // 发送邮件的服务器的IP和端口
        private String mailProtocol = "smtp";        // 邮件传输协议
        private String isEnableSmtpSSL = "true";
//        private String mailServerPort = "25";
        private String fromAddress;         // 邮件发送者的地址
        private String toAddress;           // 邮件接收者的地址
        private String userName;            // 登陆邮件发送服务器的用户名和密码
        private String password;
        private boolean validate = false;   // 是否需要身份验证
        private String subject;             // 邮件主题
        private String content;             // 邮件的文本内容
        private String[] attachFileNames;   // 邮件附件的文件名
        /**
         * 获得邮件会话属性
         */
        public Properties getProperties(){
            Properties p = new Properties();
            p.put("mail.host", this.mailServerHost);
            p.put("mail.transport.protocol", this.mailProtocol);
            p.put("mail.smtp.auth", validate?"true":"false");
            p.put("mail.smtp.ssl.enable", this.isEnableSmtpSSL);
//            p.put("mail.smtp.port", this.mailServerPort);
            try {
                MailSSLSocketFactory mailSSLsf = new MailSSLSocketFactory();
                mailSSLsf.setTrustAllHosts(true);   //设置信任所有主机
                p.put("mail.smtp.ssl.socketFactory", mailSSLsf);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return p;
        }

        public String getMailServerHost() {
            return mailServerHost;
        }

        public void setMailServerHost(String mailServerHost) {
            this.mailServerHost = mailServerHost;
        }

//        public String getMailServerPort() {
//            return mailServerPort;
//        }

//        public void setMailServerPort(String mailServerPort) {
//            this.mailServerPort = mailServerPort;
//        }

        public String getFromAddress() {
            return fromAddress;
        }

        public void setFromAddress(String fromAddress) {
            this.fromAddress = fromAddress;
        }

        public String getToAddress() {
            return toAddress;
        }

        public void setToAddress(String toAddress) {
            this.toAddress = toAddress;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public boolean isValidate() {
            return validate;
        }

        public void setValidate(boolean validate) {
            this.validate = validate;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String[] getAttachFileNames() {
            return attachFileNames;
        }

        public void setAttachFileNames(String[] attachFileNames) {
            this.attachFileNames = attachFileNames;
        }
    }
    //邮件认证
    public static class MailAuthenticator extends Authenticator {
        String username = null;
        String password = null;

        public MailAuthenticator(){}
        public MailAuthenticator(String username, String password) {
            this.username = username;
            this.password = password;
        }
        @Override
        protected PasswordAuthentication getPasswordAuthentication(){
            return new PasswordAuthentication(username, password);
        }
    }
}
