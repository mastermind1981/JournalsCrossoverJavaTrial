package com.crossover.trial.journals.service;

import com.crossover.trial.journals.model.User;

import java.util.Map;

/**
 * Generic email service for sending any email with provided model map and template
 */
public interface EmailService {

  void sendEmail(User user, Map model, String templateName);
}
