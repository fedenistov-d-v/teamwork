package com.starbank.recommendation.controller;

import com.starbank.recommendation.model.RuleDto;
import com.starbank.recommendation.service.RuleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/rule")
public class RuleController {
    private final RuleService ruleService;

    public RuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    /**
     * Создает новое динамическое правило.
     * Ожидает JSON {@link RuleDto} в теле запроса.
     * Возвращает ruleDto правила.
     *
     * @param ruleDto данные нового правила
     * @return ruleDto правила
     */
    @PostMapping
    public RuleDto createRule(@RequestBody RuleDto ruleDto) {
        return ruleService.createRuleForProduct(ruleDto);
    }

    /**
     * Получает список всех динамических правил.
     * Ответ содержит поле "data" с массивом правил.
     *
     * @return Map с ключом "data" и списком правил
     */
    @GetMapping
    public Map<String, List<RuleDto>> listAllRules() {
        return ruleService.getAllRules();
    }

    /**
     * Удаляет правило по ID.
     * URL: /rule/{id}
     * При успехе возвращает пустой ответ (статус 204 выставляется автоматически фреймворком для void).
     *
     * @param id идентификатор удаляемого правила
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public void deleteRule(@PathVariable Long id) {
        ruleService.deleteRule(id);
    }
}