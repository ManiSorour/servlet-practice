package service;

import model.role.Admin;
import model.role.Inspector;
import model.role.InventoryManager;
import model.role.User;
import repository.UserJpaRepository;

import java.util.List;
public class AuthService {

    private final UserJpaRepository userJpaRepository = new UserJpaRepository();

    public User login(String username, String password) {

        List<User> users = userJpaRepository.findAll();


        for (User user : users) {

            if (user.getUsername().equals(username) && user.getPasswordHash().equals(password)) {
                return user;
            }
        }

        return null;
    }


}
