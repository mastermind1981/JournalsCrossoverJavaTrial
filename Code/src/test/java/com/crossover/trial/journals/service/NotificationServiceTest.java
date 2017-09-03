package com.crossover.trial.journals.service;

import com.crossover.trial.journals.Application;
import com.crossover.trial.journals.model.Category;
import com.crossover.trial.journals.model.Journal;
import com.crossover.trial.journals.repository.CategoryRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.MimeMessage;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class NotificationServiceTest extends BaseEmailTest{

  @Autowired
  private NotificationService notificationService;

  @Autowired
  private CategoryRepository categoryRepository;

  private Journal newJournal;

  @Before
  public void setup() {
    super.setup();

    Category category = categoryRepository.findOne(3l);

    newJournal = new Journal();
    newJournal.setName("Endocrinology Today");
    newJournal.setCategory(category);
  }

  @Test
  public void sendJournalIsPublishedEmailTest() {
    notificationService.sendJournalIsPublishedEmail(newJournal);
    MimeMessage[] messages = greenMail.getReceivedMessages();
    assertEquals("1 message should be sent, for subscriber only",1, messages.length);
  }

  @Test
  public void sendJournalDigestTest() {
    List<Journal> journals = new ArrayList<>();
    journals.add(newJournal);
    notificationService.sendJournalDigest(journals);
    MimeMessage[] messages = greenMail.getReceivedMessages();
    assertEquals("4 message should be sent,for all uasers",4, messages.length);
  }

  @Test
  public void sendJournalDigestWithEmptyListTest() {
    List<Journal> journals = new ArrayList<>();
    notificationService.sendJournalDigest(journals);
    MimeMessage[] messages = greenMail.getReceivedMessages();
    assertEquals("Email should not be sent for empty list", 0, messages.length);
  }
}
