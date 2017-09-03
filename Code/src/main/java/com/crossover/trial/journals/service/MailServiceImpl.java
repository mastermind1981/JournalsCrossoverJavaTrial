package com.crossover.trial.journals.service;

import com.crossover.trial.journals.model.Journal;
import com.crossover.trial.journals.model.User;
import com.crossover.trial.journals.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class MailServiceImpl implements MailService {

  private JavaMailSender javaMailSender;
  private VelocityEngine velocityEngine;
  private UserRepository userRepository;

  private final static Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

  @Autowired
  public MailServiceImpl(JavaMailSender javaMailSender, VelocityEngine velocityEngine, UserRepository userRepository) {
    this.javaMailSender = javaMailSender;
    this.velocityEngine = velocityEngine;
    this.userRepository = userRepository;
  }

  @Override
  public void sendJournalIsPublishedEmail(Journal journal) {
    List<User> subscribedUsers = userRepository.findBySubscriptionsCategory(journal.getCategory());
    for (User user : subscribedUsers) {
      Map model = new HashMap();
      model.put("user", user);
      model.put("journal", journal);
      sendEmail(user, model, "new-arrivals.vm");
    }
  }

  @Override
  public void sendJournalDigest(List<Journal> journals) {
    if (journals != null && !journals.isEmpty()) {
      List<User> allUsers = userRepository.findAll();
      for (User user : allUsers) {
        Map model = new HashMap();
        model.put("user", user);
        model.put("journals", journals);
        sendEmail(user, model, "digest.vm");
      }
    }
  }

  private void sendEmail(User user, Map model, String templateName) {
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
    } catch (Exception e) {
      log.error("Could not notify user with ID:{}", user.getId());
      log.error("Notification was not sent", e);
    }
  }
}
