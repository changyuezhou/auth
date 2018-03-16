package com.system.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.auth.bean.OperationException;
import com.system.auth.bean.OperationMessage;
import com.system.auth.util.SystemLogging;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;

@ControllerAdvice
@RestController
public class ErrorControllerAdvise implements ErrorController {
    private static final String PATH = "/error";

    @Override
    public String getErrorPath() {
        return PATH;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<OperationMessage> defaultFailed(Exception ex) {
        SystemLogging.LoggingException(OperationException.getServiceException(), ex.getMessage());
        OperationMessage response = new OperationMessage(OperationException.getServiceException(), ex.getMessage());

        return new ResponseEntity<OperationMessage>(response, HttpStatus.OK);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<OperationMessage> sqlFailed(Exception ex) {
        SystemLogging.LoggingException(OperationException.getSqlOpException(), ex.getMessage());
        OperationMessage response = new OperationMessage(OperationException.getSqlOpException(), ex.getMessage());

        return new ResponseEntity<OperationMessage>(response, HttpStatus.OK);
    }

    @ExceptionHandler(OperationException.class)
    public ResponseEntity<OperationMessage> operationFailed(OperationException ex) {
        SystemLogging.LoggingException(ex.getCode(), ex.getMsg());
        OperationMessage response = new OperationMessage(ex.getCode(), ex.getMsg());

        return new ResponseEntity<OperationMessage>(response, HttpStatus.OK);
    }

    @RequestMapping(value = PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<OperationMessage> errorHandler(Exception ex) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        SystemLogging.LoggingException(OperationException.getUserInputException(), ex.getLocalizedMessage() + " - " + mapper.writeValueAsString(ex.getStackTrace()));

        return new ResponseEntity<OperationMessage>(new OperationMessage(OperationException.getUserInputException(), "maybe path is not found or params is not valid, and so plz contact administrator"), HttpStatus.OK);
    }
}
