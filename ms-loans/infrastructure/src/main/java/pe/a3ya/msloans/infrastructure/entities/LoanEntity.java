package pe.a3ya.msloans.infrastructure.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "loans")
@Getter
@Setter
public class LoanEntity extends Auditory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "payment_method")
    private String paymentMethod;
    @Column(name = "payment_type")
    private String paymentType;
    @Column(name = "contract_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate  contractDate;
    @Column(name = "start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate  startDate;
    @Column(name = "end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    @Column(name = "interest_rate")
    private Double interestRate;
    @Column(name = "status")
    private String status;
    @Column(name = "term")
    private Integer term; // plazo
    @Column(name = "fee")
    private Double fee; // cuota
    @OneToMany(mappedBy = "loan", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<GuarantiesEntity> guaranties;
    @OneToMany(mappedBy = "loan", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PaymentInstallmentEntity> paymentInstallments;
}
