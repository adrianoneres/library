package me.adrianoneres.library.configuration;

import me.adrianoneres.library.controller.dto.FormValidationErrorDto;
import me.adrianoneres.library.controller.dto.MessageDto;
import me.adrianoneres.library.exception.BusinessLogicException;
import me.adrianoneres.library.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ResourceExceptionHandler {

    private MessageSource messageSource;

    @Autowired
    public ResourceExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<FormValidationErrorDto> handle(MethodArgumentNotValidException exception) {
        List<FormValidationErrorDto> errors = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        fieldErrors.forEach(error -> {
            String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            FormValidationErrorDto formValidationErrorDto = new FormValidationErrorDto(error.getField(), message);

            errors.add(formValidationErrorDto);
        });

        return errors;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public MessageDto handle(IllegalArgumentException exception) {
        return new MessageDto(exception.getMessage());
    }

    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(BusinessLogicException.class)
    public MessageDto handle(BusinessLogicException exception) {
        return new MessageDto(exception.getMessage());
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataNotFoundException.class)
    public MessageDto handle(DataNotFoundException exception) {
        return new MessageDto(exception.getMessage());
    }
}
