package com.example.todoapp.repository;

import com.example.todoapp.model.entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {

    @Modifying
    @Query(
            value = "TRUNCATE TABLE to_do",
            nativeQuery = true
    )
    void truncateTable();
}
