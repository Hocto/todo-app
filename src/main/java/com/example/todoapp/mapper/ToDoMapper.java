package com.example.todoapp.mapper;

import com.example.todoapp.model.dto.ToDoDto;
import com.example.todoapp.model.entity.ToDo;
import com.example.todoapp.model.request.ToDoRequest;
import com.example.todoapp.model.response.ToDoResponse;
import org.springframework.stereotype.Component;

@Component
public class ToDoMapper {

    public ToDoDto fromEntityToDto(ToDo entity) {
        return ToDoDto.builder().id(entity.getId()).value(entity.getValue()).build();
    }

    public ToDo fromDtoToEntity(ToDoDto dto) {
        var toDo = new ToDo();

        toDo.setId(dto.getId());
        toDo.setValue(dto.getValue());

        return toDo;
    }

    public ToDoDto fromRequestToDto(ToDoRequest request) {

        return ToDoDto.builder().value(request.getValue()).build();

    }

    public ToDoResponse fromDtoToResponse(ToDoDto dto) {

        return ToDoResponse.builder().id(dto.getId()).value(dto.getValue()).build();
    }
}
