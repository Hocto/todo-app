package com.example.todoapp.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ToDoListResponse {

    private List<ToDoResponse> item;
}
