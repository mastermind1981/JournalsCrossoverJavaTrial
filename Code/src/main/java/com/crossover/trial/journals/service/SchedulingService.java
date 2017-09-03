package com.crossover.trial.journals.service;

import com.crossover.trial.journals.model.Journal;
import com.crossover.trial.journals.repository.JournalRepository;

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

  @Autowired
  public SchedulingService(JournalRepository journalRepository, NotificationService notificationService) {
    this.journalRepository = journalRepository;
    this.notificationService = notificationService;
  }

  @Scheduled(cron = "*/2 * * * *")
  public void sendDigest() {
    Date fromDate = Date.from(LocalDateTime.now().minusMinutes(1).toInstant(OffsetDateTime.now().getOffset()));
    List<Journal> newJournals = journalRepository.findByPublishDateGreaterThan(fromDate);
    notificationService.sendJournalDigest(newJournals);
  }

}
