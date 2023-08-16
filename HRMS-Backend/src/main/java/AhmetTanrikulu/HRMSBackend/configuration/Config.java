package AhmetTanrikulu.HRMSBackend.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class Config {
 @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private  String port;
    @Value("${spring.mail.username}")
    private String mail;
    @Value("${spring.mail.password}")
    private String password;

    /*# Email properties for Gmail
    spring.mail.host=smtp.gmail.com
    spring.mail.port=587
    spring.mail.username=your-gmail@gmail.com
    spring.mail.password=your-gmail-password
    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.smtp.starttls.enable=true*/


    @Bean
    public JavaMailSenderImpl getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(Integer.parseInt(port));
        mailSender.setUsername(mail);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }

}
