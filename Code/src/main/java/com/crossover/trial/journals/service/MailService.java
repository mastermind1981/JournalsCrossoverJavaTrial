package com.crossover.trial.journals.service;

import com.crossover.trial.journals.model.Journal;

import java.util.List;

public interface MailService {

  void sendJournalIsPublishedEmail(Journal journal);

  void sendJournalDigest(List<Journal> journals);

}
