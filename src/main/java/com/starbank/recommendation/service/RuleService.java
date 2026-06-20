package com.starbank.recommendation.service;

import com.starbank.recommendation.model.RuleDto;
import com.starbank.recommendation.model.RuleEntity;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ProductRulesMapper mapper;
    private final RuleRepository ruleRepository;

    public RuleService(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    /**
     * Удаляет правило рекомендаций
     *
     * @param id идентификатор удаляемого правила
     */
    @Transactional
    public void deleteRule(Long id) {
        ruleRepository.deleteRuleById(id);
    }

    /**
     * Получает все правила
     *
     * @return Возвращает Map с массивом правил RuleDto
     */
    @Transactional
    public Map<String, List<RuleDto>> getAllRules() {
        List<RuleDto> data = ruleRepository.getAll();
        return Map.of("data", data);
    }

    /**
     * Создает новое правило для банковского продукта
     *
     * @param ruleDto DTO вновь создаваемого правила по продукту со всеми его подправилами
     * @return Возвращает сущность нового правила
     */
    @Transactional
    public RuleEntity createRuleForProduct(RuleDto ruleDto) {
        RuleEntity entity = mapper.toEntity(ruleDto);
        return ruleRepository.save(entity);
    }
}
