import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

// Creează proiect .
// Creează utilizator  ,
// Creează task   ,
// Atribuie task unui utilizator  ,
// Adaugă comentariu la un task ,
// Adaugă etichetă la un task
// Filtrează taskuri după status
// Sortează taskuri după deadline

public class Service {
    final private List<Task> tasks = new ArrayList<>();
    final private Set<User> users = new HashSet<>();
    final private Set<Team> teams = new HashSet<>();
    private Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public void addTask(Task task) {
        tasks.add(task);
        log.log(Level.INFO, "Task added {" + tasks.size() + "}");
    }

    public void addUser(User user) {
        users.add(user);
        log.log(Level.INFO, "User added {" + users.size() + "}");
    }

    public void addTeam(Team team) {
        teams.add(team);
        log.log(Level.INFO, "Team added {" + teams.size() + "}");
    }

    public boolean taskToUser(long idt, long idu) {
        for (var i : tasks) {
            if (i.getId() == idt) {
                var u = users.stream().filter((e) -> e.getId() == idu).findFirst();
                if (u.isEmpty()) {
                    log.log(Level.INFO, "idu NOT found");
                    return false;
                }
                i.setAssigneduser(u.get());
                return true;
            }

        }
        log.log(Level.INFO, "idt NOT found");
        return false;
    }

    public boolean addComment(long idt, long idu, String commentText) {
        Task task = tasks.stream().filter(t -> t.getId() == idt).findFirst().orElse(null);
        if (task == null) {
            log.log(Level.INFO, "Task not found: idt=" + idt);
            return false;
        }

        User user = users.stream().filter(u -> u.getId() == idu).findFirst().orElse(null);
        if (user == null) {
            log.log(Level.INFO, "User not found: idu=" + idu);
            return false;
        }

        Comment comment = new Comment(
                System.currentTimeMillis(), // simplu ID temporar
                commentText,
                user,
                LocalDateTime.now()
        );

        task.getComments().add(comment);
        log.log(Level.INFO, "Comment added to task: " + idt);
        return true;
    }

    public boolean addLabelToTask(long taskId, long userId, String labelText) {
        Task task = tasks.stream().filter(t -> t.getId() == taskId).findFirst().orElse(null);
        if (task == null) {
            log.log(Level.INFO, "Task not found: id=" + taskId);
            return false;
        }

        User user = users.stream().filter(u -> u.getId() == userId).findFirst().orElse(null);
        if (user == null) {
            log.log(Level.INFO, "User not found: id=" + userId);
            return false;
        }

        Label label = new Label(
                System.currentTimeMillis(), // ID temporar
                labelText,
                user,
                task,
                LocalDateTime.now()
        );

        task.getLabels().add(label);
        log.log(Level.INFO, "Label added to task: " + taskId);
        return true;
    }

    public List<Task> filterTasksByStatus(TaskStatus status) {
        List<Task> result = tasks.stream()
                .filter(task -> task.getStatus() == status)
                .toList();

        log.log(Level.INFO, "Filtered tasks by status " + status + ": " + result.size() + " tasks found");
        return result;
    }


    public List<Task> filterTasksByDeadline(TaskStatus status) {
        return tasks.stream().sorted((a,b)->a.getDeadline().compareTo(b.getDeadline())).toList();
    }
}
