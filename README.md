# Recommendation Service

Сервис персональных рекомендаций банковских продуктов. Анализирует транзакции клиента и на основе набора правил предлагает релевантные продукты (Инвестиции 500, Простой кредит, Надёжный вклад).

## Стек технологий

- **Java 17**, Spring Boot 3.5
- **PostgreSQL** — основная база (правила рекомендаций)
- **H2** (read-only, file-based) — данные транзакций
- **Redis** — кэширование рекомендаций
- **Liquibase** — миграции схемы PostgreSQL
- **Spring Data JPA** — работа с PostgreSQL
- **Spring Data JDBC** — работа с H2
- **Swagger/OpenAPI** (springdoc) — документация API
- **MapStruct** — маппинг DTO/сущностей
- **Lombok** — генерация бойлерплейта

## Архитектура

```
controller → service → rule (chain of responsibility)
                ↓
          repository (JPA → PostgreSQL, JDBC → H2)
```

- **Правила рекомендаций** — цепочка обязанностей: `HandlerUserOf`, `HandlerActiveUserOf`, `HandlerTransactionSumCompare`, `HandlerTransactionSumCompareDepositWithdraw`
- **Статические правила**: `RuleInvest500`, `RuleSimpleLoan`, `RuleTopSaving`
- **Динамические правила** — хранятся в PostgreSQL в JSONB-формате, управляются через `RuleController`
- **Кэш Redis** — результаты рекомендаций кэшируются на 10 минут

## API

### Рекомендации

| Метод | Путь | Описание |
|-------|------|----------|
| GET | `/recommendation/{userId}`        | Получить рекомендации для клиента (динамические правила) |
| GET | `/recommendation/static/{userId}` | Получить рекомендации для клиента (статические правила)  |

### Правила (CRUD)

| Метод | Путь | Описание |
|-------|------|----------|
| GET | `/rule` | Все правила |
| GET | `/rule/{id}` | Правило по ID |
| POST | `/rule` | Создать правило |
| DELETE | `/rule/{id}` | Удалить правило |

Swagger UI: `http://localhost:8080/swagger-ui.html`

## Запуск

### Требования

- JDK 17
- Docker (PostgreSQL + Redis)

### Запуск инфраструктуры

```bash
docker run -d --name postgres -e POSTGRES_DB=recommendations -e POSTGRES_USER=service_recommendations -e POSTGRES_PASSWORD=seva8090 -p 5432:5432 postgres:16
docker run -d --name redis -p 6379:6379 redis:7
```

### Сборка и запуск

```bash
./mvnw spring-boot:run
```

## База данных

- **PostgreSQL** — таблица `rule_entity` (правила в JSONB), управляется Liquibase
- **H2** — файл `transaction.mv.db` с транзакциями клиентов (read-only)
