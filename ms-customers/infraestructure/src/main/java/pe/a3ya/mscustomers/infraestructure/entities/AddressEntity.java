package pe.a3ya.mscustomers.infraestructure.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
public class AddressEntity extends Auditory{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "department", nullable = false, length = 100)
    private String department;
    @Column(name = "province", nullable = false, length = 100)
    private String province;
    @Column(name = "district", nullable = false, length = 100)
    private String district;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "street", nullable = false, length = 100)
    private String street;
    @Column(name = "number", nullable = false, length = 10)
    private String number;
    @Column(name = "reference", length = 100)
    private String reference;
    @Column(name = "postal_code", nullable = false, length = 10)
    private String postalCode;
    @Column(name = "latitude", nullable = false, length = 20)
    private String latitude;
    @Column(name = "longitude", nullable = false, length = 20)
    private String longitude;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;


}
