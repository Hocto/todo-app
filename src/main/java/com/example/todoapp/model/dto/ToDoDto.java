package com.example.todoapp.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ToDoDto {

    private Long id;
    private String value;
}
