package com.ravi.Todoapp.controller;

import com.ravi.Todoapp.entity.TodoEntity;
import com.ravi.Todoapp.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class TodoController {

    private final TodoRepository todoRepository;

    @GetMapping({"", "/", "/home"})
    public String showHomePage(Model model) {
        model.addAttribute("todos",todoRepository.findAll());
        return "index";
    }

    @PostMapping("/add-todo")
    public String addTodo(@RequestParam String title){
        TodoEntity newTodo = TodoEntity.builder()
                .title(title)
                .complete(false)
                .build();
        todoRepository.save(newTodo);
        return "redirect:/";
    }

    @GetMapping("/update/{id}")
    public String updateTodo(@PathVariable Long id) {
        TodoEntity existingTodo=todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found" + id));

        existingTodo.setComplete(true);
        todoRepository.save(existingTodo);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id) {
        TodoEntity existingTodo = todoRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Task not found"+ id));
        todoRepository.deleteById(id);
        return "redirect:/";
    }
}
