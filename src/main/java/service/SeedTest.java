package service;

import model.role.Admin;
import model.role.Inspector;
import repository.UserJpaRepository;

public class SeedTest {
    public static void main(String[] args) {
        UserJpaRepository repo = new UserJpaRepository();
        repo.save(new Admin(0, "milad", "2"));
        repo.save(new Inspector(0, "sati", "zia"));
        System.out.println("Seed done!");

    }
}
