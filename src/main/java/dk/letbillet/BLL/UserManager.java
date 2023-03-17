package dk.letbillet.BLL;

import dk.letbillet.database.UserDatabaseDAO;
import dk.letbillet.entity.User;
import dk.letbillet.services.UserService;

public class UserManager {

    private UserDatabaseDAO userDAO;

    public UserManager() {
        userDAO = new UserDatabaseDAO();
    }

    /**
     * This method logs in the user
     * @param username The username to login
     * @param password The password to the user
     * @return true if log in success. False otherwise.
     */
    public boolean logIn(String username, String password) {
        if(username == null || username.isEmpty()) return false;
        if(password == null) return false;

        User user = userDAO.getUser(username);

        if(user == null) return false;

        if(!user.doesPasswordsMatch(password)) return false;

        // Log in user here since password match
        UserService.getInstance().setLoggedInUser(user);

        return true;
    }

}
