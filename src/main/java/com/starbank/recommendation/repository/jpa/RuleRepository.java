package com.starbank.recommendation.repository.jpa;

import com.starbank.recommendation.model.RuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("jpaRuleRepository")
@EnableJpaRepositories(basePackages = "com.starbank.recommendation.repository.jpa")
public interface RuleRepository extends JpaRepository<RuleEntity, Long> {

    List<RuleEntity> findAll();
}
