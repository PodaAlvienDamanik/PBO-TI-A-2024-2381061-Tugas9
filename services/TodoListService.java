package services;

import  entities.toDoList;

public interface TodoListService {
    toDoList[] getTodoList();
    void addTodoList(String todo);
    Boolean  removeTodoList(Integer number);
    Boolean editTodoList(Integer number, String todo);
}
