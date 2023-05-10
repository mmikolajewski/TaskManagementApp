package pl.javastart.tasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        TaskRepository taskRepository = context.getBean(TaskRepository.class);
        populateTestData(taskRepository);

    }

    public static void populateTestData(TaskRepository taskRepository) {
        taskRepository.save(new Task("Nauka Springa", "Nauczyć się obsługiwać bazy danych w Springu",Category.HOUSE, 90));
        taskRepository.save(new Task("Poprawić budżet domowy", "Sprawdzić arkusz, który błędnie liczy budżet domowy", Category.FINANCE, 50));
        taskRepository.save(new Task("Auto do mechanika", "Umówić i zawieźć auto do mechanika na przegląd",Category.HOUSE, 80));
        taskRepository.save(new Task("Zaplanować wakacje", "Wyszukać i zaklepać wakacje w biurze podróży",Category.PERSONAL, 70));
        taskRepository.save(new Task("Kupić farbę", "Kupić białą farbę do odmalowania mieszkania",Category.HOUSE, 30));
    }
}
