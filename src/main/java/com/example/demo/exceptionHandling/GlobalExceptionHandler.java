package com.example.demo.exceptionHandling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationError>> handleValidationException(MethodArgumentNotValidException exception) {
        List<ValidationError> validationErrors = exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        log.debug(exception.getMessage());
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MatchNotFoundException.class)
    public ResponseEntity<List<ValidationError>> handleMatchNotFoundException(MatchNotFoundException e){
        ValidationError validationError = new ValidationError(e.getMatchId(), "Match not found with matchId: " + e.getMatchId());
        log.info("Match not found with matchId: " + e.getMatchId());
        return new ResponseEntity<>(List.of(validationError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RiotAccountNotFoundException.class)
    public ResponseEntity<List<ValidationError>> handleRiotAccountNotFoundException(RiotAccountNotFoundException e){
        ValidationError validationError = new ValidationError(e.getGameName(), "Riot account not found with name and tag combination: " + e.getGameName() + "#" + e.getTagLine());
        log.info("Riot account not found with name and tag combination: " + e.getGameName() + "#" + e.getTagLine());
        return new ResponseEntity<>(List.of(validationError), HttpStatus.BAD_REQUEST);
    }
}