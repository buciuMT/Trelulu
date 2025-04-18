import java.time.LocalDateTime;

public class Comment extends Id_er {
    private String text;
    private User author;
    private LocalDateTime timestamp;

    public Comment(long id,String text, User author, LocalDateTime timestamp) {
        super(id);
        this.text = text;
        this.author = author;
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}

