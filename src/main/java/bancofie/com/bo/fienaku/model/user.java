package bancofie.com.bo.fienaku.model;

import java.util.*;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class user implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "User ID", example = "1", type = "long")
    private Long id;

    @Schema(description = "userName", example = "Perez", type = "String")
    private String username;  
        
    @Schema(description = "Name", example = "Juan", type = "String")
    private String name;

    @Schema(description = "Last Name", example = "Perez", type = "String")
    private String lastname;
  
    @Schema(description = "job", example = "Technological innovation", type = "String")
    private String job;

    @Schema(description = "floor", example = "Piso 23", type = "Integer")
    private String floor;
    
    @Schema(description = "phone", example = "78426548", type = "Integer")
    private String phone;
              
    @Schema(description = "Mail", example = "juan@email.com", type = "String")
    @Column(unique = true, length = 60)
    private String mail;
    
    @Schema(description = "Account", example = "11515143", type = "Integer")
    @Column(unique = true, length = 60)
    private int account;

    @Schema(description = "User Image", example = "localhost:8080/img/img.png", type = "String")
    private String image;

    @Schema(description = "User Type ID", type = "User_Type")
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "userType")
    private userType usertype;        

    @JsonIgnore
    @Schema(description = "Password", example = "123", type = "String")
    private String password;

    @Schema(description = "Creation Date", example = "11-11-2011", type = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_At", nullable = false, updatable = false)
    private Date create;

    @Schema(description = "Update Date", example = "12-11-2011", type = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_At")
    private Date update;

    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "fienaku_User",
            joinColumns = @JoinColumn(name = "user_Id"),
            inverseJoinColumns = @JoinColumn(name = "fienaku_Id")
    )
    private List<fienaku> fienaku;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<charge> charge;
      
    @PrePersist
    public void prePersist() {
        this.create = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.update = new Date();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roleName = this.usertype != null ? this.usertype.name() : "USER_ROLE";
        return Collections.singleton(new SimpleGrantedAuthority(roleName));
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
