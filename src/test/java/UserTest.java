import dk.letbillet.entity.Role;
import dk.letbillet.entity.User;
import dk.letbillet.entity.UserDTO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {

    @Test
    void UserPasswordMatch() {
        String password1 = "password";
        String password2 = "gtsefd546g1";
        User user1 = new User(1, "username", null, password1);
        User user2 = new User(1, "username", null, password2);

        assertTrue(user1.doesPasswordsMatch(password1));
        assertTrue(user2.doesPasswordsMatch(password2));
    }

    @Test
    void UserPasswordNotMatch() {
        String password1 = "password";

        User user1 = new User(1, "username", null, password1);

        assertFalse(user1.doesPasswordsMatch("not the password"));
    }

    @Test
    void UserDTOCanCreateUserObject() {
        Role role = new Role(1, "role");
        int userid = 42;
        UserDTO userDTO = new UserDTO("username", "password", role);

        User user = userDTO.convertToUser(userid);

        boolean correctId = user.getId() == userid;
        boolean correctUsername = userDTO.getUsername().equals(user.getUsername());
        boolean correctPassword = user.doesPasswordsMatch(userDTO.getPassword());

        assertTrue(correctId);
        assertTrue(correctUsername);
        assertTrue(correctPassword);


    }
}
