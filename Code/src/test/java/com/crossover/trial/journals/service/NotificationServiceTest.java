package com.crossover.trial.journals.service;

import com.crossover.trial.journals.Application;
import com.crossover.trial.journals.model.Category;
import com.crossover.trial.journals.model.Journal;
import com.crossover.trial.journals.repository.CategoryRepository;
import com.crossover.trial.journals.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class NotificationServiceTest {

  private EmailService emailService = Mockito.mock(EmailService.class);

  @Autowired
  private UserRepository userRepository;

  private NotificationService notificationService;

  @Autowired
  private CategoryRepository categoryRepository;

  private Journal newJournal;

  @Before
  public void setup(){

    notificationService = new NotificationServiceImpl(emailService, userRepository);

    Category category = categoryRepository.findOne(3l) ;

    newJournal = new Journal();
    newJournal.setName("Endocrinology Today");
    newJournal.setCategory(category);
  }

  @Test
  public void sendJournalIsPublishedEmailTest() {
    notificationService.sendJournalIsPublishedEmail(newJournal);
    Mockito.verify(emailService, times(1)).sendEmail(any(), any(), any());
  }

  @Test
  public void sendJournalDigestTest() {
    List<Journal> journals = new ArrayList<>();
    journals.add(newJournal);
    notificationService.sendJournalDigest(journals);
    Mockito.verify(emailService, times(4)).sendEmail(any(), any(), any());
  }

  @Test
  public void sendJournalDigestWithEmptyListTest() {
    List<Journal> journals = new ArrayList<>();
    notificationService.sendJournalDigest(journals);
    Mockito.verify(emailService, times(0)).sendEmail(any(), any(), any());
  }
}
