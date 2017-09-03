package com.crossover.trial.journals.service;

import com.crossover.trial.journals.model.User;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

  private JavaMailSender javaMailSender;
  private VelocityEngine velocityEngine;

  private final static Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

  @Autowired
  public EmailServiceImpl(JavaMailSender javaMailSender, VelocityEngine velocityEngine) {
    this.javaMailSender = javaMailSender;
    this.velocityEngine = velocityEngine;
  }

  public void sendEmail(User user, Map model, String templateName) {
    try {
      MimeMessagePreparator preparator = mimeMessage -> {
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
        message.setTo(user.getEmail());
        message.setFrom("noreply@crossover.com");

        String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, StandardCharsets.UTF_8.name(), model);
        message.setText(text, true);
        message.setSubject("News service updates");
      };
      javaMailSender.send(preparator);
      log.debug("Notification is sent to user ID:{}", user.getId());
    } catch (Exception e) {
      log.error("Could not notify user with ID:{}", user.getId());
      log.error("Notification was not sent", e);
    }
  }
}
