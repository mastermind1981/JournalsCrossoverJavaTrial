package com.crossover.trial.journals.service;

import com.crossover.trial.journals.model.User;

import java.util.Map;

public interface EmailService {

  void sendEmail(User user, Map model, String templateName);
}
