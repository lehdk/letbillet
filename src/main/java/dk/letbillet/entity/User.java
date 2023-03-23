package dk.letbillet.entity;

public class User {

    private int id;
    private String username;
    private Role role;
    private String password;

    public User(int id, String username, Role role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public User(int id, String username, Role role, String password) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return getUsername();
    }

    public boolean doesPasswordsMatch(String testPassword) {
        return this.password.equals(testPassword);
    }
}
