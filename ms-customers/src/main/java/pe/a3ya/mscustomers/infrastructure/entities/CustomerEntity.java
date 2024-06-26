package pe.a3ya.mscustomers.infrastructure.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
public class CustomerEntity extends Auditory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "document_type_id", nullable = false)
    private char documentType;
    @Column(name = "document_number", nullable = false, length = 20)
    private String documentNumber;
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;
    @Column(name = "mother_last_name", nullable = false, length = 100)
    private String motherLastName;
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    @Column(name = "phone", nullable = false, length = 10)
    private String phone;
    @Column(name = "date_birth", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateBirth;
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<AddressEntity> addresses;
}
