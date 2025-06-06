import java.util.List;
import java.util.Objects;

public class User extends Id_er {

    private String role;
    private List<Task> assignedTasks;

    public User(long id, String role, List<Task> assignedTasks) {
        super(id);
        this.role = role;
        this.assignedTasks = assignedTasks;
    }

    public String getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(role, user.role) && Objects.equals(assignedTasks, user.assignedTasks) && id == user.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, assignedTasks, id);
    }

}