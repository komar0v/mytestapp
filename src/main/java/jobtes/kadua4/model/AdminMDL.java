package jobtes.kadua4.model;

import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "administrators")
public class AdminMDL {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID id; 
    private String username;
    private String password;
    private String role;
    
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    

    
}
