package com.crossover.trial.journals.service;

import com.crossover.trial.journals.model.Journal;
import com.crossover.trial.journals.repository.JournalRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

@Service
public class SchedulingService {

  private JournalRepository journalRepository;

  private NotificationService notificationService;

  private final static Logger log = LoggerFactory.getLogger(SchedulingService.class);

  @Autowired
  public SchedulingService(JournalRepository journalRepository, NotificationService notificationService) {
    this.journalRepository = journalRepository;
    this.notificationService = notificationService;
  }

  //  @Scheduled(cron = "0 2 0 ? * *")
  @Scheduled(cron = "0 0/2 * * * ?")
  public void sendDigest() {
    log.info("Scheduled action executed");
    Date fromDate = Date.from(LocalDateTime.now().minusMinutes(2).toInstant(OffsetDateTime.now().getOffset()));
    List<Journal> newJournals = journalRepository.findByPublishDateGreaterThan(fromDate);
    notificationService.sendJournalDigest(newJournals);
  }

}
