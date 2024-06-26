package br.com.itauseguros.infrastructure.rest.handler;

import br.com.itauseguros.application.exceptions.InvalidCategoryException;
import br.com.itauseguros.application.exceptions.InvalidProductNameException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.OffsetDateTime;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler({InvalidCategoryException.class, InvalidProductNameException.class,
            NullPointerException.class, IllegalArgumentException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception exception) {
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .timestamp(OffsetDateTime.now())
                .message(exception.getMessage())
                .status(400)
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .path("/products")
                .build());

    }

    @ExceptionHandler({ HttpClientErrorException.class, HttpMediaTypeException.class})
    public ResponseEntity<ErrorResponse> handleInternalServerError(Exception exception) {
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .timestamp(OffsetDateTime.now())
                .message(exception.getMessage())
                .status(500)
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .path("/products")
                .build());

    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ErrorResponse> handleNotImplemented(Exception exception) {
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .timestamp(OffsetDateTime.now())
                .message(exception.getMessage())
                .status(501)
                .error(HttpStatus.NOT_IMPLEMENTED.getReasonPhrase())
                .path("/products")
                .build());
    }

    @ExceptionHandler({ NoResourceFoundException.class })
    public ResponseEntity<ErrorResponse> handleNotFound(Exception exception) {
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .timestamp(OffsetDateTime.now())
                .message(exception.getMessage())
                .status(404)
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .path("/products")
                .build());
    }
}
