package pe.a3ya.msloans.infrastructure.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "guaranties")
@Getter
@Setter
public class GuarantiesEntity extends Auditory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "estimated_value", nullable = false, precision = 24, scale = 6)
    private BigDecimal estimatedValue;
    @Column(name = "status", nullable = false, length = 1)
    private String status;
    @Column(name = "image_url", nullable = false, length = 255)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id", nullable = false)
    private LoanEntity loan;

}
