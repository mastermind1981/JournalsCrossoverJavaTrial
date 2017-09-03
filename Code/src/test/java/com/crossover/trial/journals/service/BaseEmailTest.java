package com.crossover.trial.journals.service;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(value = "test")
public class BaseEmailTest {

  @Value("${mail.port}")
  private int maiPort;

  @Value("${mail.host}")
  private String mailHost;

  @Value("${mail.protocol}")
  private String mailProtocol;

  protected GreenMail greenMail;

  @Before
  public void setup() {
    greenMail = new GreenMail(new ServerSetup(maiPort, mailHost, mailProtocol));
    greenMail.start();
  }


  @After
  public void after() {
    greenMail.stop();
  }
}
