package com.crossover.trial.journals.service;

public interface FileService {
    String getFileName(long publisherId, String uuid);

    String getDirectory(long id);
}
