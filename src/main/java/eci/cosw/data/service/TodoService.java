/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eci.cosw.data.service;

import eci.cosw.data.model.Todo;
import java.util.List;

/**
 *
 * @author fabian
 */
interface TodoService {
    public List<Todo> getTodoList();

    public Todo addTodo(Todo todo);
}