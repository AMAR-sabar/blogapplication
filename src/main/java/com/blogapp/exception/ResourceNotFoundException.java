package com.blogapp.exception;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
    String fileName;
    Integer fieldValue;

    public ResourceNotFoundException(String resourceName, String fileName, Integer fieldValue) {
        super(String.format("%s not found with %s : %s",resourceName,fileName,fieldValue));
        this.resourceName = resourceName;
        this.fileName = fileName;
        this.fieldValue = fieldValue;
    }
}
