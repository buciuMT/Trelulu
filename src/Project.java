import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Project extends Id_er {
    private String name;
    private String description;
    private LocalDateTime deadline;
    private Team team;
    final private List<Task> tasks = new ArrayList<>();

    public Project(long id, String name, Team team, String description, LocalDateTime deadline) {
        super(id);
        this.team = team;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addTask(Task task) {
        var index = Collections.binarySearch(tasks, task);
        if (index < 0)
            index = -index - 1;
        tasks.add(index, task);
    }

    public boolean removeTaskid(long id) {
        return tasks.removeIf((x) -> {
            return ((Id_er) x).equals(new Id_er(id));
        });
    }

    public Task getTaskbyID(long id) {
        var index = Collections.binarySearch(tasks, new Id_er(id));
        if (index < 0)
            return null;
        return tasks.get(index);
    }

    public Task getRunOnTasks(Predicate<Task> func) {
        for (var i : tasks)
            if (func.test(i))
                return i;
        return null;
    }

}
