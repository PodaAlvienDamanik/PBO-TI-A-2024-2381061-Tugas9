package services;

import entities.toDoList;

import repositories.TodoListRepository;

public class TodoListServiceImpl  implements TodoListService{
    private final TodoListRepository todoListRepository;

    public TodoListServiceImpl(final TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    @Override
    public toDoList[] getTodoList() {
        return todoListRepository.getAll();
    }

    @Override
    public void addTodoList(String todo) {
        if (todo.isEmpty() || todo.isBlank()){
            System.out.println("Masukkan todo dengan benar");
            return;
        }

        toDoList todolist = new toDoList();
        todolist.setTodo(todo);
        todoListRepository.add(todolist);
    }

    @Override
    public Boolean removeTodoList(Integer number) {
        return removeTodoList(number);
    }

    @Override
    public Boolean editTodoList(Integer number, String todo) {
        toDoList todolist = new toDoList();
        todolist.setId(number);
        todolist.setTodo(todo);
        return todoListRepository.edit(todolist);
    }
}
