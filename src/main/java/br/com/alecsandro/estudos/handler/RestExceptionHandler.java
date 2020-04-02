package br.com.alecsandro.estudos.handler;

import br.com.alecsandro.estudos.error.ResourseNotFoundDetails;
import br.com.alecsandro.estudos.error.ResourseNotFoundException;
import br.com.alecsandro.estudos.error.ValidationErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourseNotFoundException.class)
    public ResponseEntity<?> handleResourseNotFoundException(ResourseNotFoundException rfnException) {
        ResourseNotFoundDetails rnfDetails = ResourseNotFoundDetails.Builder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Recurso não encontrado")
                .detail(rfnException.getMessage())
                .developerMessage(rfnException.getClass().getName())
                .build();
        return new ResponseEntity<>(rnfDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException manvException) {
        List<FieldError> fieldErrors = manvException.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
        String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));

        ValidationErrorDetails veDetails = ValidationErrorDetails.Builder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Erro na validação do campo")
                .detail("Erro na validação do campo")
                .developerMessage(manvException.getClass().getName())
                .fiel(fields)
                .fieldMessage(fieldMessages)
                .build();
        return new ResponseEntity<>(veDetails, HttpStatus.BAD_REQUEST);
    }
}
