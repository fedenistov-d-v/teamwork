package com.starbank.recommendation.service;

import com.starbank.recommendation.model.RuleDto;
import com.starbank.recommendation.model.RuleEntity;
import com.starbank.recommendation.repository.jpa.RuleRepository;
import com.starbank.recommendation.rule.RuleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * Сервис динамических правил
 * Инжектирует репозиторий динамических правил по продуктам банка RuleRepository
 */
@Service
public class RuleService {

    private final RuleRepository ruleRepository;
    private final RuleMapper ruleMapper;

    public RuleService(RuleRepository ruleRepository, RuleMapper ruleMapper) {
        this.ruleRepository = ruleRepository;
        this.ruleMapper = ruleMapper;
    }

    /**
     * Удаляет правило рекомендаций
     *
     * @param id идентификатор удаляемого правила
     */
    @Transactional
    public void deleteRule(Long id) {
        ruleRepository.deleteById(id);
    }

    /**
     * Получает все правила
     *
     * @return Возвращает Map с массивом правил RuleDto
     */
    @Transactional
    public Map<String, List<RuleDto>> getAllRules() {
        List<RuleEntity> data = ruleRepository.findAll();

        return Map.of("data", ruleMapper.toDtoList(data));
    }

    /**
     * Создает новое правило для банковского продукта
     *
     * @param ruleDto DTO вновь создаваемого правила по продукту со всеми его подправилами
     * @return Возвращает DTO нового правила
     */
    @Transactional
    public RuleDto createRuleForProduct(RuleDto ruleDto) {
        RuleEntity entity = ruleMapper.toEntity(ruleDto);
        RuleEntity savedEntity = ruleRepository.save(entity);
        return  ruleMapper.toDto(savedEntity);
    }
}
