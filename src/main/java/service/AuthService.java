package service;

import model.role.Admin;
import model.role.Inspector;
import model.role.InventoryManager;
import model.role.User;

import java.util.List;
public class AuthService {


    private final List<User> users = List.of(
            new Admin(4, "milad", "2")
            ,new Inspector(2,"sati","zia"),
            new InventoryManager(3,"h","h"),
            new Admin(1,"mamad","mamad")
    );

    public User login(String username, String password) {

        for (User user : users) {

            if (user.getUsername().equals(username) && user.getPasswordHash().equals(password)) {
                return user;
            }
        }

        return null;
    }


}
