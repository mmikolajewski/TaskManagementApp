package pl.javastart.tasks;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    String list(Model model, @RequestParam( defaultValue = "ALL",required = false) Category category, @RequestParam(required = false) Long deleteid) {

        if (deleteid != null) {
            Task task = taskRepository.findById(deleteid).orElseThrow();
            task.setCompletionTime(LocalDateTime.now());
            taskRepository.save(task);
        }

        List<Task> taskList;
        if (!(category.equals(Category.ALL))) {
            taskList = taskRepository.findByCategoryAndCompletionTimeIsNull(category);
        } else {
            taskList = taskRepository.findAllByCompletionTimeIsNull();
        }

        model.addAttribute("taskList", taskList);

        return "/list";

    }

    @GetMapping("/archive")
    String archive(Model model,
                   @RequestParam( defaultValue = "ALL",required = false) Category category) {

        List<Task> taskList;
        if (!(category.equals(Category.ALL))) {
            taskList = taskRepository.findByCategoryAndCompletionTimeIsNotNull(category);
        } else {
            taskList = taskRepository.findAllByCompletionTimeIsNotNull();
        }

        model.addAttribute("taskList", taskList);

        return "/list";

    }

    @GetMapping("/edit")
    public String showTaskEditForm(@RequestParam Long id, Model model) {
        Optional<Task> taskOptional = taskRepository.findById(id);

        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            model.addAttribute("taskToEdit", task);

            return "editTask";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/edit")
    public String editTask(@RequestParam Long id, Task task) {
        Task returnedTask = taskRepository.findById(id).orElseThrow();

        returnedTask.setTitle(task.getTitle());
        returnedTask.setDescription(task.getDescription());
        returnedTask.setCategory(task.getCategory());
        returnedTask.setPriority(task.getPriority());

        taskRepository.save(returnedTask);

        return "redirect:/";
    }

    @GetMapping("/add")
    public String taskToCreate(Model model) {
        Task task = new Task();
        model.addAttribute("taskToCreate", task);
        return "add";
    }

    @PostMapping("/add")
    public String addTask(Task task) {
        task.setStartTime(LocalDateTime.now());

        taskRepository.save(task);

        return "redirect:/";
    }

}
