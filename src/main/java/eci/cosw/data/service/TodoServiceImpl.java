/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eci.cosw.data.service;

import eci.cosw.data.TodoRepository;
import eci.cosw.data.TodoRepository;
import eci.cosw.data.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fabian
 * 
 */

@Service
public class TodoServiceImpl implements TodoService{

    @Autowired
    TodoRepository todoRepository;
    
   
    @Override
    public List<Todo> getTodoList() {
        return todoRepository.findAll();
    }

   
    @Override
    public Todo addTodo(Todo todo) {
        System.out.println("added new todo: " + todo);
        todoRepository.save(todo);
        return todo;
    }
}