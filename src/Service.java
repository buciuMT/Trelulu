import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Service {
    private final AuditService log = AuditService.getInstance();

    public Service() throws IOException {
    }

    public void addTask(Task task) {
        try {
            TaskService.getInstance().create(task);
            AuditService.log(Level.INFO, "Task added to DB: " + task.getId());
        } catch (Exception e) {
            try {
                AuditService.log(Level.SEVERE, "Error adding task " + e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void addUser(User user) {
        try {
            UserService.getInstance().create(user);
            AuditService.log(Level.INFO, "User added to DB: " + user.getId());
        } catch (Exception e) {
            try {
                AuditService.log(Level.SEVERE, "Error adding user " + e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public boolean taskToUser(long taskId, long userId) {
        try {
            var task = TaskService.getInstance().read(taskId);
            var user = UserService.getInstance().read(userId);

            if (task == null) {
                AuditService.log(Level.INFO, "Task not found: " + taskId);
                return false;
            }
            if (user == null) {
                AuditService.log(Level.INFO, "User not found: " + userId);
                return false;
            }

            task.setAssigneduser(user);
            TaskService.getInstance().update(task);
            AuditService.log(Level.INFO, "Task assigned to user: " + userId);
            return true;
        } catch (Exception e) {
            try {
                AuditService.log(Level.SEVERE, "Error assigning task "+ e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return false;
        }
    }

    public boolean addComment(long taskId, long userId, String commentText) {
        try {
            var task = TaskService.getInstance().read(taskId);
            var user = UserService.getInstance().read(userId);

            if (task == null) {
                AuditService.log(Level.INFO, "Task not found: " + taskId);
                return false;
            }
            if (user == null) {
                AuditService.log(Level.INFO, "User not found: " + userId);
                return false;
            }

            var comment = new Comment(
                    System.currentTimeMillis(),
                    commentText,
                    user,
                    LocalDateTime.now()
            );

            CommentService.getInstance().create(comment);
            task.getComments().add(comment);
            TaskService.getInstance().update(task);

            AuditService.log(Level.INFO, "Comment added to task: " + taskId);
            return true;
        } catch (Exception e) {
            try {
                AuditService.log(Level.SEVERE, "Error adding comment"+ e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return false;
        }
    }

    public boolean addLabelToTask(long taskId, long userId, String labelText) {
        try {
            var task = TaskService.getInstance().read(taskId);
            var user = UserService.getInstance().read(userId);

            if (task == null) {
                AuditService.log(Level.INFO, "Task not found: " + taskId);
                return false;
            }
            if (user == null) {
                AuditService.log(Level.INFO, "User not found: " + userId);
                return false;
            }

            var label = new Label(
                    System.currentTimeMillis(),
                    labelText,
                    user,
                    task,
                    LocalDateTime.now()
            );

            LabelService.getInstance().create(label);
            task.getLabels().add(label);
            TaskService.getInstance().update(task);

            AuditService.log(Level.INFO, "Label added to task: " + taskId);
            return true;
        } catch (Exception e) {
            try {
                AuditService.log(Level.SEVERE, "Error adding label"+ e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return false;
        }
    }

    public List<Task> filterTasksByStatus(TaskStatus status) {
        try {
            var tasks = TaskService.getInstance().readAll();
            var result = tasks.stream()
                    .filter(task -> task.getStatus() == status)
                    .toList();

            AuditService.log(Level.INFO, "Filtered tasks by status " + status + ": " + result.size());
            return result;
        } catch (Exception e) {
            try {
                AuditService.log(Level.SEVERE, "Error filtering tasks"+ e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return List.of();
        }
    }

    public List<Task> filterTasksByDeadline(TaskStatus status) {
        try {
            var tasks = TaskService.getInstance().readAll();
            return tasks.stream()
                    .sorted((a, b) -> a.getDeadline().compareTo(b.getDeadline()))
                    .toList();
        } catch (Exception e) {
            try {
                AuditService.log(Level.SEVERE, "Error filtering by deadline "+ e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return List.of();
        }
    }
}
