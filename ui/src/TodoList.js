import React, { useState, useEffect } from 'react';

const TodoList = () => {
  const [todos, setTodos] = useState([]);
  const [todoText, setTodoText] = useState('');

  useEffect(() => {
    fetch('http://localhost:8080/api/v1/todos')
      .then((response) => response.json())
      .then((data) => setTodos(data));
  }, []);

  const addTodo = () => {
    fetch('http://localhost:8080/api/v1/todo', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ value: todoText }),
    })
      .then((response) => response.json())
      .then((data) => {
        setTodoText('');
        setTodos([...todos, data]);
      });
  };

  return (
    <div>
      <h1>To Do App</h1>
      <input
        type="text"
        placeholder="Enter a new todo"
        value={todoText}
        onChange={(e) => setTodoText(e.target.value)}
      />
      <button onClick={addTodo}>Add</button>
      <ul>
        {todos.map((todo) => (
          <li key={todo.id}>{todo.value}</li>
        ))}
      </ul>
    </div>
  );
};

export default TodoList;