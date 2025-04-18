import java.time.LocalDateTime;

public class Notification extends Id_er {
    private String text;
    private LocalDateTime timesamp;
    private User user;

    public Notification(long id, String text, LocalDateTime timesamp, User user) {
        super(id);
        this.text = text;
        this.timesamp = timesamp;
        this.user = user;
    }
}
