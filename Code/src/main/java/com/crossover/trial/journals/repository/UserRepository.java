package com.crossover.trial.journals.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crossover.trial.journals.model.Category;
import com.crossover.trial.journals.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLoginName(String loginName);

    List<User> findBySubscriptionsCategory(Category category);

}
