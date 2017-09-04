package com.crossover.trial.journals.service;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FileServiceImpl implements FileService {

  @Value("${journal.file.path.pattern}")
  private String filePattern;

  @Value("${journal.directory.path.pattern}")
  private String directoryPattern;

  private final static Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

  @Override
  public byte[] getJournalFileAsByteArray(long publisherId, String journalUUID) {
    File file = new File(getJournalFilePath(publisherId, journalUUID));
    try {
      InputStream in = new FileInputStream(file);
      return IOUtils.toByteArray(in);
    } catch (IOException e) {
      throw new ServiceException("Requested journal does not exist", e);
    }

  }

  @Override
  public void uploadJournalFile(long publisherId, String journalUUID, InputStream inputStream) {
    File dir = new File(getPublisherDirectoryPath(publisherId));
    createDirectoryIfNotExist(dir);
    File newJournalFile = new File(getJournalFilePath(publisherId, journalUUID));

    try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newJournalFile))) {
      FileCopyUtils.copy(inputStream, stream);
    } catch (IOException e) {
      throw new ServiceException("Upload failed", e);
    }
  }

  @Override
  public void deleteJournalFile(long publisherId, String journalUUID) {
    String filePath = getJournalFilePath(publisherId, journalUUID);
    File file = new File(filePath);
    if (file.exists()) {
      boolean deleted = file.delete();
      if (!deleted) {
        log.error("File {} cannot be deleted", filePath);
      }
    }
  }

  private boolean createDirectoryIfNotExist(File dir) {
    if (!dir.exists()) {
      boolean created = dir.mkdirs();
      if (!created) {
        return false;
      }
    }
    return true;
  }

  private String getJournalFilePath(long publisherId, String uuid) {
    return String.format(filePattern, publisherId, uuid);
  }

  private String getPublisherDirectoryPath(long id) {
    return String.format(directoryPattern, id);
  }

}
