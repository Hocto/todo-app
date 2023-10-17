package com.example.todoapp.util;

import com.example.todoapp.model.entity.ToDo;

import java.math.BigDecimal;
import java.util.List;

public class ToDoTestUtil {

    public static List<ToDo> getToDos() {

        return List.of(
                createEntity(1L, "Homework"),
                createEntity(2L, "Travel"),
                createEntity(3L, "Shopping")
        );
    }

    private static ToDo createEntity(Long id, String value) {

        ToDo toDo = new ToDo();

        toDo.setId(id);
        toDo.setValue(value);
        return toDo;
    }
}
