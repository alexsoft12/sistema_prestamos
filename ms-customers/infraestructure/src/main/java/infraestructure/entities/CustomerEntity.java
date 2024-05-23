package infraestructure.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customers")
@Getter
@Setter
public class CustomerEntity extends Auditory{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
