package com.example.todoapp.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class ToDoRequest {

    @NotBlank
    @Size(max = 255)
    private String value;
}
