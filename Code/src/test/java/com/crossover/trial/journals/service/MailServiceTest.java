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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class MailServiceTest {

  @Autowired
  MailService mailService;

  @Autowired
  private CategoryRepository categoryRepository;

  private Journal newJournal;

  @Before
  public void setup(){
    Category category = categoryRepository.findOne(3l) ;

    newJournal = new Journal();
    newJournal.setName("Endocrinology Today");
    newJournal.setCategory(category);
  }

  @Test
  public void sendJournalIsPublishedEmailTest() {
    mailService.sendJournalIsPublishedEmail(newJournal);
  }
}
