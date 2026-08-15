package model.role;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User{


    public Admin(int id, String username, String passwordHash) {
        super(id, username, passwordHash, Role.ADMIN);
    }

    protected Admin() {
    }

    @Override
    public boolean canViewPrices() {
        return true;
    }

    @Override
    public boolean canEditStock() {
        return true;
    }

    @Override
    public boolean canViewReports() {
        return true;
    }
}
