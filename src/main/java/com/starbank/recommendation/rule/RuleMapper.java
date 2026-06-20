package com.starbank.recommendation.rule;

import com.starbank.recommendation.model.RuleDto;
import com.starbank.recommendation.model.RuleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RuleMapper {

    RuleMapper INSTANCE = Mappers.getMapper(RuleMapper.class);

    @Mapping(target = "id", ignore = true)
    RuleEntity toEntity(RuleDto ruleDto);

    RuleDto toDto(RuleEntity ruleEntity);

    List<RuleDto> toDtoList(List<RuleEntity> entities);

    List<RuleEntity> toEntityList(List<RuleDto> dtos);
}