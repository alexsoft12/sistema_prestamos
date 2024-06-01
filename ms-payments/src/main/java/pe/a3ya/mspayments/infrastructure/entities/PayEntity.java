package pe.a3ya.mspayments.infrastructure.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "pay")
@Getter
@Setter
public class PayEntity extends Auditory{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "installments_id" )
    private Long installmentsId;
    @Column(name = "day" )
    private Timestamp day;
    @Column(name = "modality" )
    private String modality;
    @Column(name = "method" )
    private String method;
    @Column(name = "amount" )
    private double amount;

}
