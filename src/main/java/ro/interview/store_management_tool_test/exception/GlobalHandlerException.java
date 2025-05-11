package ro.interview.store_management_tool_test.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ro.interview.store_management_tool_test.dto.ExceptionDto;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleProductNotFound(ProductNotFoundException ex) {
        return ResponseEntity.status(NOT_FOUND).body(ExceptionDto.builder().message(ex.getMessage()).build());
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ExceptionDto> handleProductAlreadyExists(ProductAlreadyExistsException ex) {
        return ResponseEntity.status(CONFLICT).body(ExceptionDto.builder().message(ex.getMessage()).build());
    }
}
