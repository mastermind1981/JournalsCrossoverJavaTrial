package com.crossover.trial.journals.service;

import com.crossover.trial.journals.Application;
import com.crossover.trial.journals.model.Category;
import com.crossover.trial.journals.model.Journal;
import com.crossover.trial.journals.model.User;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)

public class EmailServiceTest extends BaseEmailTest {

  @Autowired
  private EmailService emailService;


  private Journal newJournal;
  private User user;

  @Before
  public void setup() {
    super.setup();

    user = new User();
    user.setLoginName("Test");
    user.setEmail("test@crossover.com");

    Category category = new Category();
    category.setName("Some Category");

    newJournal = new Journal();
    newJournal.setName("Endocrinology Today");
    newJournal.setCategory(category);
  }

  @Test
  public void sendEmailTest() throws IOException, MessagingException {

    Map<String, Object> model = new HashMap<>();
    model.put("journal", newJournal);
    model.put("user", user);

    emailService.sendEmail(user, model, "new-arrivals.vm");
    MimeMessage[] messages = greenMail.getReceivedMessages();

    InputStream stream = getClass().getClassLoader().getResourceAsStream("new-arrivals-email.html");
    String emailContent = IOUtils.toString(stream);
    assertEquals(emailContent, messages[0].getContent());

  }


}
