import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class Task extends Id_er {
    private String title;
    private String description;
    private TaskStatus status;
    private List<Label> labels = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private User assigneduser;
    private LocalDateTime deadline;
    private FileAttachment file; //TODO: implement file tracking

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getAssigneduser() {return assigneduser;}

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Task(long id, String title, String description, LocalDateTime deadline, TaskStatus status) {
        super(id);
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.status = status;
    }

    public void setAssigneduser(User assigneduser) {
        this.assigneduser = assigneduser;
    }


    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", labels=" + labels +
                ", comments=" + comments +
                ", assigneduser=" + assigneduser +
                '}';
    }

    public Label getLabelByID(long id) {
        var index = Collections.binarySearch(labels, new Id_er(id));
        if (index < 0)
            return null;
        return labels.get(index);
    }

    public Label getRunOnLabels(Predicate<Label> func) {
        for (var i : labels)
            if (func.test(i))
                return i;
        return null;
    }

    public Comment getCommentByID(long id) {
        var index = Collections.binarySearch(comments, new Id_er(id));
        if (index < 0)
            return null;
        return comments.get(index);
    }

    public Comment getRunOnComments(Predicate<Comment> func) {
        for (var i : comments)
            if (func.test(i))
                return i;
        return null;
    }
}
