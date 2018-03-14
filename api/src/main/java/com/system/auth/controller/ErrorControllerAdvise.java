package com.system.auth.controller;

import com.system.auth.bean.OperationException;
import com.system.auth.bean.OperationMessage;
import com.system.auth.util.SystemLogging;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class ErrorControllerAdvise {

    @ExceptionHandler(Exception.class)
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
}
