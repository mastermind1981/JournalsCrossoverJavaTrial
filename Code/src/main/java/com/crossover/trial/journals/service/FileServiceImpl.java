package com.crossover.trial.journals.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {

  @Value("${journal.file.path.pattern}")
  private String filePattern;

  @Value("${journal.directory.path.pattern}")
  private String directoryPattern;

  @Override
  public String getFileName(long publisherId, String uuid) {
    return String.format(filePattern, publisherId, uuid);
  }

  @Override
  public String getDirectory(long id) {
    return String.format(directoryPattern, id);
  }

}
