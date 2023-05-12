package pl.javastart.tasks;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByCompletionTimeIsNullOrderByPriorityDesc();
    List<Task> findByCategoryAndCompletionTimeIsNullOrderByPriorityDesc(Category category);
    List<Task> findAllByCompletionTimeIsNotNull();
    List<Task> findByCategoryAndCompletionTimeIsNotNull(Category category);

}
