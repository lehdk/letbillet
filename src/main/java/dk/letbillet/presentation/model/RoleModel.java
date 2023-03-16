package dk.letbillet.presentation.model;

import dk.letbillet.BLL.RoleManager;
import dk.letbillet.entity.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RoleModel {

    private RoleManager roleManager;

    private ObservableList<Role> rolesObservableList;

    public RoleModel() {
        roleManager = new RoleManager();
        rolesObservableList = FXCollections.observableArrayList(roleManager.getAllRoles());
    }

    public ObservableList<Role> getRolesObservableList() {
        return rolesObservableList;
    }

}
