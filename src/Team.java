import java.util.HashSet;
import java.util.Set;

public class Team extends Id_er {
    private String name;
    final private Set<User> members = new HashSet<>();

    public Team(long id, String name) {
        super(id);
        this.name = name;
    }

    public boolean addMember(User member) {
        return members.add(member);
    }

    public boolean removeMember(long id) {
        return (members.removeIf((User u) -> {
            return u.getId() == id;
        }));
    }

}
