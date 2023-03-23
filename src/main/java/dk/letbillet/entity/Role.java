package dk.letbillet.entity;

public class Role {

    private int id;
    private String roleName;

    public Role(int id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
