package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class HomeController {
    @Autowired
    TaskRepository taskRepository;

    @RequestMapping("/")
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskRepository.findAll());
        return "tasklist";
    }

    @GetMapping("/add")
    public String addTask(Model model) {
        model.addAttribute("task", new Task());
        return "addTask";
    }

    @PostMapping("/add")
    public String checkTask(@Valid Task task, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "addTask";
        } else {
            taskRepository.save(task);
            return "redirect:/";
        }
    }

    @RequestMapping("/update/{id}")
    public String update(@PathVariable("id") long id, Model model) {
        model.addAttribute("task", taskRepository.findById(id).get());
        return "addTask";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        taskRepository.deleteById(id);
        return "redirect:/";
    }
}
