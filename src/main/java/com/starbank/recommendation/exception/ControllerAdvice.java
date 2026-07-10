package com.starbank.recommendation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;

/**
 * Глобальный обработчик исключений для веб-слоя приложения Starbank.
 * <p>
 * Этот класс перехватывает ошибки, возникающие на этапе обработки запросов контроллерами,
 * изолирует их от клиента и возвращает стандартизированный ответ в формате {@link ApiError}.
 * Предотвращает утечку системных трейсов во внешнюю среду.
 * </p>
 *
 * @author Твоё Irina Razinkova
 * @version 1.0
 */

@RestControllerAdvice
public class ControllerAdvice {

    /**
     * Обрабатывает ошибки несовпадения типов параметров в URL (Path Variables).
     * <p>
     * Метод динамически определяет ожидаемый тип данных (UUID для рекомендаций
     * или Long/Integer для баланса студентов) и формирует кастомное понятное сообщение
     * для каждого случая.
     * </p>
     *
     * @param ex исключение несовпадения типов параметров запроса
     * @return объект {@link ResponseEntity} со статусом 400 (Bad Request) и описанием ошибки
     */

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        Class<?> requiredType = ex.getRequiredType();
        String typeName = (requiredType != null) ? requiredType.getSimpleName() : "";

        String message = switch (typeName) {
            case "UUID" ->
                    String.format("Параметр '%s' должен быть валидным UUID (например, cd515076-5d8a...)", ex.getName());
            case "Long", "Integer" -> String.format("Параметр '%s' должен быть целым числом", ex.getName());
            default -> String.format("Параметр '%s' имеет некорректный тип данных", ex.getName());
        };

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Invalid Path Variable",
                message
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает ошибки чтения JSON-тела запроса.
     * <p>
     * Срабатывает, если входящий JSON имеет синтаксические ошибки (нарушена структура),
     * либо если типы данных в JSON невозможно десериализовать в поля целевого DTO.
     * </p>
     *
     * @param ex исключение синтаксического анализа или чтения HTTP-сообщения
     * @return объект {@link ResponseEntity} со статусом 400 (Bad Request)
     */

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleReadableException(HttpMessageNotReadableException ex) {
        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Malformed JSON Request",
                "Некорректный формат JSON-тела запроса"
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает вызовы эндпоинтов неподдерживаемыми HTTP-методами.
     * <p>
     * Например, если отправлен POST-запрос на адрес, который ожидает исключительно GET.
     * </p>
     *
     * @param ex исключение неподдерживаемого HTTP-метода
     * @return объект {@link ResponseEntity} со статусом 405 (Method Not Allowed)
     */

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                "Method Not Allowed",
                String.format("HTTP метод '%s' не поддерживается для этого адреса", ex.getMethod())
        );
        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Обрабатывает ошибки отсутствия обязательных параметров в строке запроса (Query Parameters).
     * <p>
     * Срабатывает, когда в запросе отсутствует параметр, помеченный в контроллере как обязательный.
     * </p>
     *
     * @param ex исключение отсутствия обязательного параметра запроса
     * @return объект {@link ResponseEntity} со статусом 400 (Bad Request)
     */

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> handleMissingParam(MissingServletRequestParameterException ex) {
        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Missing Parameter",
                String.format("Отсутствует обязательный параметр запроса: %s", ex.getParameterName())
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает ошибки отсутствия ресурса по-указанному URL (404 Not Found).
     * <p>
     * Срабатывает, если клиент пытается вызвать эндпоинт, который не зарегистрирован
     * в контроллерах приложения.
     * </p>
     *
     * @param ex исключение отсутствия ресурса
     * @return объект {@link ResponseEntity} со статусом 404 (Not Found)
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiError> handleNoResourceFound(NoResourceFoundException ex) {
        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Resource Not Found",
                String.format("Путь '%s' не найден на сервере", ex.getResourcePath())
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }

    /**
     * Обрабатывает бизнес-исключения, связанные с отсутствием данных в БД (404 Not Found).
     * <p>
     * Срабатывает, когда сервис-слой запрашивает запись из базы данных по ID (UUID или Long),
     * но сущность не обнаруживается.
     * </p>
     *
     * @param ex исключение отсутствия сущности в системе
     * @return объект {@link ResponseEntity} со статусом 404 (Not Found)
     */
    @ExceptionHandler(NotFound.class)
    public ResponseEntity<ApiError> handleEntityNotFound(NotFound ex) {
        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Entity Not Found",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
