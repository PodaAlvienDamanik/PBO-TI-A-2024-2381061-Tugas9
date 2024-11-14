package repositories;

import config.Database;
import entities.toDoList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TodoListRepositoryDbImpl implements  TodoListRepository{
    private final Database database;

    public TodoListRepositoryDbImpl(Database database) {
        this.database = database;
    }

    @Override
    public toDoList[] getAll() {
        Connection connection = database.getConnection();
        String sqlStatement = "SELECT * FROM todos";
        List<toDoList> todoLists = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                toDoList todoList = new toDoList();
                Integer id = resultSet.getInt(1);
                String todo = resultSet.getString(2);
                todoList.setTodo(todo);
                todoList.setId(id);
                todoLists.add(todoList);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return todoLists.toArray(toDoList[]::new);
    }

    @Override
    public void add(toDoList todolist) {
        Connection connection = database.getConnection();
        String sqlStatement = "INSERT INTO todos(todo) VALUE(?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1,todolist.getTodo());

            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected > 0){
                System.out.println("Insert Succesfull !");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private Integer getDbId(final Integer number){
        toDoList[] todoLists = getAll();
        Long countElement = Arrays.stream(todoLists).filter(Objects::nonNull).count();

        if (countElement.intValue() == 0){
            return null;
        }
        var dbId = todoLists[number - 1].getId();
        return dbId;
    }



    @Override
    public Boolean remove(final Integer number){
        String sqlStatement = "DELETE FROM todos WHERE id = ?";
        Connection connection = database.getConnection();
        var dbId = getDbId(number);
        if (dbId == null){
            return false;
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1,dbId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0){
                System.out.println("Delete Succesfull!");
                return true;
            }
            return false;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean edit(final toDoList todolist) {
        String sqlStatement = "UPDATE todos set todos = ? WHERE id = ?";
        Connection connection = database.getConnection();
        var dbId = getDbId(todolist.getId());
        if (dbId == null){
            return false;
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1,todolist.getTodo());
            preparedStatement.setInt(2,dbId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0){
                System.out.println("Update succesfull !");
                return false;
            }
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
