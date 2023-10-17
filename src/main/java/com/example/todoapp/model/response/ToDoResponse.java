package com.example.todoapp.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ToDoResponse {

    private Long id;
    private String value;
}
