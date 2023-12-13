package dev.mehdi.aftas.exception.handler;

import dev.mehdi.aftas.exception.InvalidRequestException;
import dev.mehdi.aftas.exception.ResourceExistException;
import dev.mehdi.aftas.exception.ResourceNotFoundException;
import dev.mehdi.aftas.exception.response.ErrorResponse;
import dev.mehdi.aftas.exception.response.ErrorValidationResponse;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex
            , WebRequest request) throws Exception {

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            Exception ex, WebRequest request) throws Exception {

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage()
                , request.getDescription(false));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ResourceExistException.class)
    public final ResponseEntity<ErrorResponse> handleResourceExistException(
            Exception ex, WebRequest request) throws Exception {

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage()
                , request.getDescription(false));

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ErrorResponse body = new ErrorResponse(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(body, headers, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex
            , HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        if (ex.getValue() != null) {
            errors.put(ex.getPropertyName(), ex.getValue().toString());
        }
        ErrorValidationResponse errorResponse =
                new ErrorValidationResponse(
                        request.getDescription(false), errors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers
            , HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        entry -> {
                            String v = entry.getDefaultMessage();
                            return v != null ? v : "";
                        }
                ));

        ErrorValidationResponse response = new ErrorValidationResponse(
                request.getDescription(false), errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public final ResponseEntity<ErrorValidationResponse> handleInvalidRequestException(
            InvalidRequestException ex, WebRequest request) {

        ErrorValidationResponse errorResponse = new ErrorValidationResponse(
                ex.getMessage(), ex.getErrors());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
