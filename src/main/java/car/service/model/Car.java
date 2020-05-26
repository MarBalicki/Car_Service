package car.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;

@Data
@Entity
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

}
