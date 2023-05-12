package pl.javastart.tasks;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Controller
public class TaskController {

    private final TaskRepository taskRepository;
    private final TaskService taskService;

    public TaskController(TaskRepository taskRepository, TaskService taskService) {
        this.taskRepository = taskRepository;
        this.taskService = taskService;
    }

    @GetMapping("/")
    String home() {

        return "/home";
    }

    @GetMapping("/list")
    String list(Model model, @RequestParam(required = false) Category category) {

        List<Task> taskList;
        if (category != null) {
            taskList = taskRepository.findByCategoryAndCompletionTimeIsNullOrderByPriorityDesc(category);
        } else {
            taskList = taskRepository.findAllByCompletionTimeIsNullOrderByPriorityDesc();
        }

        model.addAttribute("taskList", taskList);

        return "/list";

    }
    @GetMapping(value = "/list", params = "startId")
    String start(@RequestParam(required = false) Long startId) {
        if (startId != null) {
            Task task = taskRepository.findById(startId).orElseThrow();
            task.setStartTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
            taskRepository.save(task);
            return "redirect:/list";
        }
        return "/list";
    }

    @GetMapping(value = "/list", params = "finishId")
    String finish(@RequestParam(required = false) Long finishId) {

        if (finishId != null) {
            Task task = taskRepository.findById(finishId).orElseThrow();
            task.setCompletionTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
            taskRepository.save(task);
            return "redirect:/list";
        }
        return "/list";
    }
    @GetMapping("/archive")
    String archive(Model model,
                   @RequestParam(required = false) Category category) {

        List<Task> taskList;
        if (category != null) {
            taskList = taskRepository.findByCategoryAndCompletionTimeIsNotNull(category);
        } else {
            taskList = taskRepository.findAllByCompletionTimeIsNotNull();
        }

        model.addAttribute("taskService", taskService);

        model.addAttribute("taskList", taskList);

        return "/archive";

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
    public String editTask(Task task) {
        Optional<Task> returnedTask = taskRepository.findById(task.getId());

        if (returnedTask.isPresent()) {
            Task task1 = returnedTask.get();
            task1.setTitle(task.getTitle());
            task1.setDescription(task.getDescription());
            task1.setCategory(task.getCategory());
            task1.setPriority(task.getPriority());

            taskRepository.save(task1);
            return "redirect:/list";
        }
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
        task.setAddTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));

        taskRepository.save(task);

        return "redirect:/list";
    }

}
