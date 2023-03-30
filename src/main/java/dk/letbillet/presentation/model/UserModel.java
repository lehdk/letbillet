package dk.letbillet.presentation.model;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.letbillet.BLL.UserManager;
import dk.letbillet.entity.Event;
import dk.letbillet.entity.User;
import dk.letbillet.entity.UserDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public class UserModel {

    private final ObservableList<User> userObservableList;
    private UserManager userManager;

    public UserModel() {
        userManager = new UserManager();

        userObservableList = FXCollections.observableList(userManager.getAllUsers());
    }

    public boolean logIn(String username, String password) {
        boolean isLoggedIn = userManager.logIn(username, password);

        return isLoggedIn;
    }

    public User createUser(UserDTO user) throws SQLException {
        User newUser = userManager.createUser(user);

        if(newUser != null) userObservableList.add(newUser);

        return newUser;
    }

    public void deleteUser(User user) throws SQLException {
        userManager.deleteUser(user);
    }

    public ObservableList<User> getUserObservableList() {
        return userObservableList;
    }

}
