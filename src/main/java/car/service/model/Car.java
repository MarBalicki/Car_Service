package car.service.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "cars")
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "registration_number")
    private String registrationNumber;
    @Enumerated(value = EnumType.STRING)
    private Brand brand;
    private String model;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "body_style")
    private BodyStyle bodyStyle;
    @Column(name = "production_year")
    private int productionYear;
    private Double capacity;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "engine_type")
    private EngineType engineType;
    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Owner owner;
    @ManyToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<Mechanic> mechanicSet;
    @OneToMany(mappedBy = "car", fetch = FetchType.EAGER)
    private Set<ServiceRequest> serviceRequestSet;

}
