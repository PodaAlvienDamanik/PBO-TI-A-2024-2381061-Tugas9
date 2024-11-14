package repositories;

import entities.toDoList;

public interface TodoListRepository {
    toDoList[] getAll();
    void add(toDoList todolist);
    Boolean remove(Integer id);
    Boolean edit(toDoList todolist);
}
