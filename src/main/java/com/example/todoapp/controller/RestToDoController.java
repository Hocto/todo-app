package com.example.todoapp.controller;

import com.example.todoapp.mapper.ToDoMapper;
import com.example.todoapp.model.dto.ToDoDto;
import com.example.todoapp.model.request.ToDoRequest;
import com.example.todoapp.model.response.ToDoResponse;
import com.example.todoapp.service.ToDoService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
@Api(tags = "To Do API")
public class RestToDoController {

    private final ToDoMapper toDoMapper;
    private final ToDoService toDoService;

    @GetMapping("/todo/{id}")
    public ResponseEntity<ToDoResponse> getToDo(@PathVariable("id") String id) {

        ToDoDto dto = toDoService.retrieveById(Long.valueOf(id));

        return new ResponseEntity<>(toDoMapper.fromDtoToResponse(dto), HttpStatus.OK);
    }

    @PostMapping("/todo")
    public ResponseEntity<ToDoResponse> createToDo(@Valid @RequestBody ToDoRequest request) {

        ToDoDto dto = toDoService.create(toDoMapper.fromRequestToDto(request));

        return new ResponseEntity<>(toDoMapper.fromDtoToResponse(dto), HttpStatus.OK);
    }

    @PutMapping("/todo/{id}")
    public ResponseEntity<ToDoResponse> modifyToDo(@PathVariable("id") String id, @Valid @RequestBody ToDoRequest request) {

        ToDoDto dto = toDoService.update(Long.valueOf(id), toDoMapper.fromRequestToDto(request));

        return new ResponseEntity<>(toDoMapper.fromDtoToResponse(dto), HttpStatus.OK);
    }

    @DeleteMapping("/todo/{id}")
    public ResponseEntity<Void> removeToDo(@PathVariable("id") String id) {

        toDoService.remove(Long.valueOf(id));

        return ResponseEntity.ok().build();
    }

    @GetMapping("/todos")
    public ResponseEntity<List<ToDoResponse>> getToDos() {

        List<ToDoResponse> response = toDoService.retrieveAll().stream()
                .map(toDoMapper::fromDtoToResponse).collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
