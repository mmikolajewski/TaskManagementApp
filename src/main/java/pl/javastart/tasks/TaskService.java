package pl.javastart.tasks;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class TaskService {

    public String printPeriod(LocalDateTime startTime, LocalDateTime completionTime) {
        LocalDateTime start = startTime;
        LocalDateTime end = completionTime;

        long noOfHours = start.until(end, ChronoUnit.HOURS);
        long noOfMinutes = start.until(end, ChronoUnit.MINUTES);

        long hours   = noOfHours / 60;
        long minutes = noOfMinutes % 60;


        return hours + ":" + minutes;
    }
}
