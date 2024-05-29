package ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false , unique = true)
    private Long id;

    private String username;

    private String email;

    private String password;

    private Boolean estado;

    @JsonIgnore
    @OneToMany(mappedBy = "userEntity")
    private List<Orden> ordenList;

    @ManyToMany(fetch = FetchType.EAGER,targetEntity = RoleEntity.class,cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_roles",joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_rol"))
    private Set<RoleEntity> roles;

}
