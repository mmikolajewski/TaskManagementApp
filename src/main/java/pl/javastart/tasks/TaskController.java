package pl.javastart.tasks;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TaskController {

    TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/")
    String home() {

        return "/home";
    }

    @GetMapping("/list")
    String list(Model model, @RequestParam(required = false) Category category) {

        List<Task> taskList;
        if (category != null) {
            taskList = taskRepository.findByCategory(category);
        } else {
            taskList = taskRepository.findAll();
        }

        model.addAttribute("taskList", taskList);

        return "/list";

    }


}
