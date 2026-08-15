package model.role;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "userType")
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password")
    private String passwordHash;
    @Enumerated(EnumType.STRING)
    private Role role;


    public User(int id, String username, String passwordHash, Role role) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    protected User() {
    }

    public abstract boolean canViewPrices();

    public abstract boolean canEditStock();

    public abstract boolean canViewReports();


    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Role getRole() {
        return role;
    }


    @Override
    public String toString() {
        return username + "(" + role + ")";
    }
}
