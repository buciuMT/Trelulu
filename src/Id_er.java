import java.util.Objects;

public class Id_er implements Comparable<Id_er> {
    protected long id;

    public Id_er(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public int compareTo(Id_er idEr) {
        if (id == idEr.id)
            return 0;
        return id < idEr.id ? -1 : 1;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Id_er idEr)) return false;
        return id == idEr.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
