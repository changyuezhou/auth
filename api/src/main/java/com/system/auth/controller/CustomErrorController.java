package com.system.auth.controller;

import com.system.auth.bean.OperationMessage;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomErrorController implements ErrorController {
    public static final int SERVICE_EXCEPTION = -100000;
    public static final int USER_INPUT_EXCEPTION = -100001;
    public static final int NOT_FOUND = -100404;
    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public OperationMessage handleError() {
        return new OperationMessage(NOT_FOUND, "Request resource not found.");
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
