package dk.letbillet.entity;

public class UserDTO {

    private String username;
    private Role role;
    private String password;

    public UserDTO(String username, String password, Role role) {
        this.username = username;
        this.role = role;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User convertToUser(int id) {
        return new User(id, username, role, password);
    }
}

