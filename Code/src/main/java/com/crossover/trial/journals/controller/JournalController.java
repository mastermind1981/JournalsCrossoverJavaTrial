package com.crossover.trial.journals.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import com.crossover.trial.journals.model.Journal;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crossover.trial.journals.model.Category;
import com.crossover.trial.journals.model.Subscription;
import com.crossover.trial.journals.model.User;
import com.crossover.trial.journals.repository.JournalRepository;
import com.crossover.trial.journals.repository.UserRepository;
import com.crossover.trial.journals.service.CurrentUser;
import com.crossover.trial.journals.service.FileService;

@Controller
public class JournalController {

	private FileService fileService;

	private JournalRepository journalRepository;

	private UserRepository userRepository;

	@Autowired
	public JournalController(FileService fileService, JournalRepository journalRepository, UserRepository userRepository) {
		this.fileService = fileService;
		this.journalRepository = journalRepository;
		this.userRepository = userRepository;
	}

	@ResponseBody
	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET, produces = "application/pdf")
	public ResponseEntity renderDocument(@AuthenticationPrincipal Principal principal, @PathVariable("id") Long id)
			throws IOException {
		Journal journal = journalRepository.findOne(id);
		Category category = journal.getCategory();
		CurrentUser activeUser = (CurrentUser) ((Authentication) principal).getPrincipal();
		User user = userRepository.findOne(activeUser.getUser().getId());
		List<Subscription> subscriptions = user.getSubscriptions();
		Optional<Subscription> subscription = subscriptions.stream()
				.filter(s -> s.getCategory().getId().equals(category.getId())).findFirst();
		if (subscription.isPresent() || journal.getPublisher().getId().equals(user.getId())) {
			File file = new File(fileService.getFileName(journal.getPublisher().getId(), journal.getUuid()));
			InputStream in = new FileInputStream(file);
			return ResponseEntity.ok(IOUtils.toByteArray(in));
		} else {
			return ResponseEntity.notFound().build();
		}

	}
}
