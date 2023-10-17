package com.example.todoapp.service;

import com.example.todoapp.exception.EntityNotFoundException;
import com.example.todoapp.mapper.ToDoMapper;
import com.example.todoapp.model.dto.ToDoDto;
import com.example.todoapp.model.entity.ToDo;
import com.example.todoapp.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ToDoService {

    private final ToDoRepository toDoRepository;

    private final ToDoMapper toDoMapper;

    public ToDoDto retrieveById(Long id) {

        var entity = toDoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Id %s does not exist", id),
                ToDo.class.getSimpleName()
        ));

        return toDoMapper.fromEntityToDto(entity);
    }

    public ToDoDto create(ToDoDto dto) {

        var savedEntity = toDoRepository.save(toDoMapper.fromDtoToEntity(dto));

        return toDoMapper.fromEntityToDto(savedEntity);
    }

    public ToDoDto update(Long id, ToDoDto dto) {

        var entity = toDoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Id %s does not exist", dto.getId()),
                ToDo.class.getSimpleName()
        ));

        entity.setValue(dto.getValue());
        var modifiedEntity = toDoRepository.save(entity);

        return toDoMapper.fromEntityToDto(modifiedEntity);

    }

    public void remove(Long id) {
        toDoRepository.deleteById(id);
    }

    public List<ToDoDto> retrieveAll() {

        return toDoRepository.findAll()
                .stream()
                .map(toDoMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }
}
