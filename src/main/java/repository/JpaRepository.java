package repository;

import model.product.Product;
import model.role.User;

import java.util.List;
import java.util.Optional;

public interface JpaRepository  {

    List<User> findAll();

    Optional<User> findById(int id);



    void save(User user);

    void update(User user);

    void delete(int id);



}
