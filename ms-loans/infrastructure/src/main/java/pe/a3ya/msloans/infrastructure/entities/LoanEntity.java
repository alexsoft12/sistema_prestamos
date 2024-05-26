package pe.a3ya.msloans.infrastructure.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name = "loans")
@Getter
@Setter
public class LoanEntity extends Auditory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    private Long guarantyId;
    private Double amount;
    private String paymentMethod;
    private String paymentType;
    private Date contractDate;
    private Date startDate;
    private Date endDate;
    private Double interestRate;
    private String status;
    private Integer term; // plazo
    private Double fee; // cuota
}
