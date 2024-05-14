package ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orden")
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double total;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UserEntity userEntity;

    @JsonIgnore//siempre pon esto,,para q no te cree un bucle infinito
    @OneToMany(mappedBy = "orden",fetch = FetchType.EAGER)
    private List<OrdenDetail> ordenDetailList;


}
