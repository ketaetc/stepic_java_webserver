package dbService.dataSets;

/**
 * edited by ketaetc
 */
@SuppressWarnings("UnusedDeclaration")
public class UsersDataSet {
    private long id;
    private String name;
    private String pass;

    public UsersDataSet(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public UsersDataSet(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UsersDataSet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
