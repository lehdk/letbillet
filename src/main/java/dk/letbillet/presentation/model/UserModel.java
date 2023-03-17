package dk.letbillet.presentation.model;

import dk.letbillet.BLL.UserManager;

public class UserModel {

    private UserManager userManager;

    public UserModel() {
        userManager = new UserManager();
    }

    public boolean logIn(String username, String password) {
        boolean isLoggedIn = userManager.logIn(username, password);

        return isLoggedIn;
    }

}
