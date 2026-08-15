package model.role;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("inspector")
public class Inspector extends User {



    public Inspector(int id, String username, String passwordHash ) {
        super(id, username, passwordHash, Role.INSPECTOR);
    }

    protected Inspector() {
    }

    @Override
    public boolean canViewPrices() {
        return false;
    }

    @Override
    public boolean canEditStock() {
        return false;
    }

    @Override
    public boolean canViewReports() {
        return true;
    }
}
