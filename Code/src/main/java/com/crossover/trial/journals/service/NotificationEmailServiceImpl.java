package com.crossover.trial.journals.service;

import com.crossover.trial.journals.model.Journal;
import com.crossover.trial.journals.model.User;
import com.crossover.trial.journals.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service for sending different type of notification via email
 */
@Service
public class NotificationEmailServiceImpl implements NotificationService {

  private EmailService emailService;
  private UserRepository userRepository;

  @Autowired
  public NotificationEmailServiceImpl(EmailService emailService, UserRepository userRepository) {
    this.emailService = emailService;
    this.userRepository = userRepository;
  }

  @Override
  public void sendJournalIsPublishedEmail(Journal journal) {
    List<User> subscribedUsers = userRepository.findBySubscriptionsCategory(journal.getCategory());
    for (User user : subscribedUsers) {
      Map model = new HashMap();
      model.put("user", user);
      model.put("journal", journal);
      emailService.sendEmail(user, model, "new-arrivals.ftl");
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
        emailService.sendEmail(user, model, "digest.ftl");
      }
    }
  }

}
