package com.example.todoapp.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends ToDoException {

    private final String entity;

    public EntityNotFoundException(String message, String entity) {
        super(message);
        this.entity = entity;
    }
}
