package com.starbank.recommendation.service;

import com.starbank.recommendation.model.RuleDto;
import com.starbank.recommendation.model.RuleEntity;
import org.mapstruct.Mapper;

/**
 * Преобразует все поля с одинаковыми именами правила из DTO в сущность и обратно
 */
@Mapper(componentModel = "spring")
public interface ProductRulesMapper {

    RuleEntity toEntity(RuleDto dto);

    RuleDto toDto(RuleEntity entity);
}