package com.example.todoapp.controller;

import com.example.todoapp.repository.ToDoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static com.example.todoapp.util.ToDoTestUtil.getToDos;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class RestToDoIntegrationTest {

    private static final String GET_TO_DO_URL = "/api/v1/todo/{id}";
    private static final String CREATE_TO_DO_URL = "/api/v1/todo";
    private static final String UPDATE_TO_DO_URL = "/api/v1/todo/{id}";
    private static final String DELETE_TO_DO_URL = "/api/v1/todo/{id}";
    private static final String GET_TO_DO_LIST_URL = "/api/v1/todos";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ToDoRepository toDoRepository;

    @Value("classpath:request/valid_create_to_do_body.json")
    private Resource createToDoBody;

    @Value("classpath:request/invalid_create_to_do_body.json")
    private Resource invalidCreateToDoBody;

    @Value("classpath:request/valid_update_to_do_body.json")
    private Resource updateToDoBody;

    @BeforeEach
    @Transactional
    void beforeEach() {
        toDoRepository.deleteAllInBatch();
        toDoRepository.saveAll(getToDos());
    }

    @Test
    void onValidRequestBodyWithCreateMethodReturnsToDoItem() throws Exception {

        MockHttpServletRequestBuilder builder = post(CREATE_TO_DO_URL)
                .content(resourceToString(createToDoBody))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(13)))
                .andExpect(jsonPath("$.value", is("Fitness")));
    }

    @Test
    void onInvalidRequestBodyWithCreateMethodReturnsBadRequest() throws Exception {

        MockHttpServletRequestBuilder builder = post(CREATE_TO_DO_URL)
                .content(resourceToString(invalidCreateToDoBody))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("value")))
                .andExpect(jsonPath("$[0].reason", is("Size")))
                .andExpect(jsonPath("$[0].message", is("size must be between 0 and 255")));
    }

    @Test
    void onValidRequestBodyWithUpdateMethodReturnsToDoItem() throws Exception {

        MockHttpServletRequestBuilder builder = put(UPDATE_TO_DO_URL.replace("{id}", "20"))
                .content(resourceToString(updateToDoBody))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(20)))
                .andExpect(jsonPath("$.value", is("Homework")));
    }

    @Test
    void onExistingPathVariableContentWithGetMethodReturnsToDoItem() throws Exception {

        MockHttpServletRequestBuilder builder = get(GET_TO_DO_URL.replace("{id}", "15"))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(15)))
                .andExpect(jsonPath("$.value", is("Travel")));
    }

    @Test
    void onNonExistingPathVariableContentWithGetMethodReturnsBadRequest() throws Exception {

        toDoRepository.deleteAll();

        MockHttpServletRequestBuilder builder = get(GET_TO_DO_URL.replace("{id}", "12"))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.entity", is("ToDo")))
                .andExpect(jsonPath("$.message", is("Id 12 does not exist")));
    }

    @Test
    void onExistingPathVariableContentWithDeleteMethodReturnsNothing() throws Exception {

        MockHttpServletRequestBuilder builder = delete(DELETE_TO_DO_URL.replace("{id}", "4"))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk());

        assertThat(toDoRepository.count()).isEqualTo(2);
    }

    @Test
    void onRetrieveAllMethodReturnsAllToDos() throws Exception {

        MockHttpServletRequestBuilder builder = get(GET_TO_DO_LIST_URL)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }
    private String resourceToString(Resource resource) throws IOException {

        InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
        return FileCopyUtils.copyToString(reader);
    }
}
