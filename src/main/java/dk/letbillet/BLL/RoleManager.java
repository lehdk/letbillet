package dk.letbillet.BLL;

import dk.letbillet.database.RoleDatabaseDAO;
import dk.letbillet.entity.Role;

import java.util.List;

public class RoleManager {

    private RoleDatabaseDAO roleDAO;

    public RoleManager() {
        roleDAO = new RoleDatabaseDAO();
    }

    public List<Role> getAllRoles() {
        return roleDAO.getAllRoles();
    }

}
