package dk.letbillet.services;

import dk.letbillet.entity.User;

public class UserService {

    private static UserService instance;

    private User loggedInUser;

    private UserService() {
        loggedInUser = null;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    public static UserService getInstance() {
        if(instance == null) {
            instance = new UserService();
        }

        return instance;
    }
}
