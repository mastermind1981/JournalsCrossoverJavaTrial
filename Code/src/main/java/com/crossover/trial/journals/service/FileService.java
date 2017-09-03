package com.crossover.trial.journals.service;

import java.io.InputStream;

public interface FileService {

    byte[] getJournalFileAsByteArray(long publisherId, String journalUUID);

    void uploadJournalFile(long publisherId, String journalUUID, InputStream inputStream);

    void deleteJournalFile(long publisherId, String journalUUID);
}
