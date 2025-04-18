import java.time.LocalDateTime;
import java.util.Objects;

public class Label extends Id_er {
    private String text;
    private User author;
    private Task task;
    private LocalDateTime timestamp;

    public Label(long id, String text, User author, Task task, LocalDateTime timestamp) {
        super(id);
        this.text = text;
        this.author = author;
        this.task = task;
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public User getAuthor() {
        return author;
    }

    public Task getTask() {
        return task;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Label label = (Label) o;
        return Objects.equals(text, label.text) && Objects.equals(author, label.author) && Objects.equals(task, label.task) && Objects.equals(timestamp, label.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), text, author, task, timestamp);
    }

    @Override
    public String toString() {
        return "Label{" +
                "text='" + text + '\'' +
                ", author=" + author +
                ", task=" + task +
                ", timestamp=" + timestamp +
                '}';
    }
}
