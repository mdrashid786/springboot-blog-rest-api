package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private String resourseName;
    private String fieldName;
    private Long fieldValue;

    public ResourceNotFoundException(String resourseName, String fieldName, Long fieldValue) {
        super(String.format("%s not found with %s : '%s'",resourseName,fieldName,fieldValue));
        this.resourseName = resourseName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourseName() {
        return resourseName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Long getFieldValue() {
        return fieldValue;
    }
}
