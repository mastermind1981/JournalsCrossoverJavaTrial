package com.crossover.trial.journals.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

  @Value("${mail.protocol}")
  private String protocol;
  @Value("${mail.host}")
  private String host;
  @Value("${mail.port}")
  private int port;
  @Value("${mail.smtp.socketFactory.port}")
  private int socketPort;
  @Value("${mail.smtp.auth}")
  private boolean auth;
  @Value("${mail.smtp.starttls.enable}")
  private boolean starttls;
  @Value("${mail.smtp.starttls.required}")
  private boolean startllsRequired;
  @Value("${mail.smtp.socketFactory.fallback}")
  private boolean fallback;
  @Value("${mail.username}")
  private String username;
  @Value("${mail.password}")
  private String password;
  @Value("${mail.smtp.socketFactory.class}")
  private String socketFactoryClass;

  @Bean
  public JavaMailSender javaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    Properties mailProperties = new Properties();
    mailProperties.put("mail.smtp.auth", auth);
    mailProperties.put("mail.smtp.starttls.enable", starttls);
    mailProperties.put("mail.smtp.starttls.required", startllsRequired);
    mailProperties.put("mail.smtp.socketFactory.port", socketPort);
    mailProperties.put("mail.smtp.socketFactory.fallback", fallback);
    mailProperties.put("mail.smtp.ssl.enable", false);
    mailProperties.put("mail.smtp.socketFactory.class", socketFactoryClass);

    mailSender.setJavaMailProperties(mailProperties);
    mailSender.setHost(host);
    mailSender.setPort(port);
    mailSender.setProtocol(protocol);
    mailSender.setUsername(username);
    mailSender.setPassword(password);
    return mailSender;
  }
}
