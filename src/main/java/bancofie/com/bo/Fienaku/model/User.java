package bancofie.com.bo.Fienaku.model;

import java.util.Date;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class User implements Serializable {

    @Schema(description = "User ID", example = "1", type = "long")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Schema(description = "Name", example = "Juan", type = "String")
    private String name;

    @NotBlank
    @Schema(description = "Last Name", example = "Perez", type = "String")
    private String lastname;

    @NotBlank
    @Schema(description = "EMail", example = "juan@email.com", type = "String")
    private String mail;

    @NotBlank
    @Schema(description = "Password", example = "123", type = "String")
    private String password;

    @NotBlank
    @Schema(description = "Account_Fie", example = "11515143", type = "int")
    private int account_fie;

    @Schema(description = "User Image", example = "localhost:8080/img/img.png", type = "String")
    private String image;
    
    @Schema(description = "User Type", example = "user ; admin ; manager", type = "Enum")
    @Enumerated(EnumType.STRING)
    private UserType user_type = UserType.USER;

    @Schema(description = "Status", example = "active ; suspended", type = "Enum")
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    @Schema(description = "Creation Date", example = "11-11-2011", type = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_at", nullable = false, updatable = false)
    private Date createdAt;

    @Schema(description = "Update Date", example = "12-11-2011", type = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_at")
    private Date updatedAt;
  
    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date();
    }

    public enum UserType {
        USER,
        ADMIN,
        MANAGER
    }

    public enum Status {
        ACTIVE,
        SUSPENDED;
    }     
    
    private static final long serialVersionUID = 1285454306356845809L;
}