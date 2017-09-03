package com.crossover.trial.journals.service;

import com.crossover.trial.journals.model.Journal;
import com.crossover.trial.journals.model.User;
import com.crossover.trial.journals.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class NotificationServiceImpl implements NotificationService {

  private EmailService emailService;
  private UserRepository userRepository;

  @Autowired
  public NotificationServiceImpl(EmailService emailService, UserRepository userRepository) {
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
      emailService.sendEmail(user, model, "new-arrivals.vm");
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
        emailService.sendEmail(user, model, "digest.vm");
      }
    }
  }

}
