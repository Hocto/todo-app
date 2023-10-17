package com.example.todoapp.service;

import com.example.todoapp.exception.EntityNotFoundException;
import com.example.todoapp.mapper.ToDoMapper;
import com.example.todoapp.model.dto.ToDoDto;
import com.example.todoapp.model.entity.ToDo;
import com.example.todoapp.repository.ToDoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ToDoServiceUnitTest {

    @Mock
    private ToDoRepository toDoRepository;

    @Mock
    private ToDoMapper toDoMapper;

    @InjectMocks
    private ToDoService toDoService;

    @Test
    void onRetrievingToDoItemByIdReturnDto() {

        ToDo toDo = new ToDo();
        toDo.setId(1L);
        toDo.setValue("Homework");

        ToDoDto givenDto = ToDoDto.builder().id(toDo.getId()).value(toDo.getValue()).build();

        when(toDoRepository.findById(1L)).thenReturn(Optional.of(toDo));
        when(toDoMapper.fromEntityToDto(any())).thenReturn(givenDto);

        ToDoDto toDoDto = toDoService.retrieveById(1L);

        assertThat(toDoDto).isNotNull();
        assertThat(toDoDto.getId()).isEqualTo(givenDto.getId());
        assertThat(toDoDto.getValue()).isEqualTo(givenDto.getValue());
    }

    @Test
    void onRetrievingToDoItemByIdWithNonExistingIdThrowsNotFoundException() {

        when(toDoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> toDoService.retrieveById(1L));
    }

    @Test
    void onCreatingToDoItemReturnDto() {

        ToDo toDo = new ToDo();
        toDo.setId(1L);
        toDo.setValue("Homework");

        ToDoDto givenDto = ToDoDto.builder().id(toDo.getId()).value(toDo.getValue()).build();

        when(toDoRepository.save(any())).thenReturn(toDo);
        when(toDoMapper.fromEntityToDto(any())).thenReturn(givenDto);
        when(toDoMapper.fromDtoToEntity(any())).thenReturn(toDo);

        ToDoDto toDoDto = toDoService.create(givenDto);

        assertThat(toDoDto).isNotNull();
        assertThat(toDoDto.getId()).isEqualTo(givenDto.getId());
        assertThat(toDoDto.getValue()).isEqualTo(givenDto.getValue());
    }

    @Test
    void onUpdatingToDoItemReturnDto() {

        String updatedValue = "Homework2";

        ToDo toDo = new ToDo();
        toDo.setId(1L);
        toDo.setValue("Homework");

        ToDoDto givenDto = ToDoDto.builder().id(toDo.getId()).value(updatedValue).build();

        when(toDoRepository.findById(1L)).thenReturn(Optional.of(toDo));
        when(toDoRepository.save(any())).thenReturn(toDo);
        when(toDoMapper.fromEntityToDto(any())).thenReturn(givenDto);

        ToDoDto toDoDto = toDoService.update(1L, givenDto);

        assertThat(toDoDto).isNotNull();
        assertThat(toDoDto.getId()).isEqualTo(givenDto.getId());
        assertThat(toDoDto.getValue()).isEqualTo(updatedValue);
    }

    @Test
    void onUpdatingToDoItemWithNonExistingIdThrowsNotFoundException() {

        ToDo toDo = new ToDo();
        toDo.setId(1L);
        toDo.setValue("Homework");

        ToDoDto givenDto = ToDoDto.builder().id(toDo.getId()).value(toDo.getValue()).build();

        when(toDoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> toDoService.update(1L, givenDto));
    }

    @Test
    void onRetrievingAllToDoItemsReturnDtoList() {

        ToDo toDo1 = new ToDo();
        toDo1.setId(1L);
        toDo1.setValue("Homework");

        ToDo toDo2 = new ToDo();
        toDo2.setId(2L);
        toDo2.setValue("Travel");

        ToDoDto givenDto1 = ToDoDto.builder().id(toDo1.getId()).value(toDo1.getValue()).build();
        ToDoDto givenDto2 = ToDoDto.builder().id(toDo2.getId()).value(toDo2.getValue()).build();

        when(toDoRepository.findAll()).thenReturn(List.of(toDo1, toDo2));
        when(toDoMapper.fromEntityToDto(toDo1)).thenReturn(givenDto1);
        when(toDoMapper.fromEntityToDto(toDo2)).thenReturn(givenDto2);

        List<ToDoDto> toDoDtoList = toDoService.retrieveAll();

        assertThat(toDoDtoList).hasSize(2);
        assertThat(toDoDtoList.get(0).getId()).isEqualTo(givenDto1.getId());
        assertThat(toDoDtoList.get(0).getValue()).isEqualTo(givenDto1.getValue());
        assertThat(toDoDtoList.get(1).getId()).isEqualTo(givenDto2.getId());
        assertThat(toDoDtoList.get(1).getValue()).isEqualTo(givenDto2.getValue());
    }
}
