package car.service.model;

import car.service.YesNoBooleanConverter;
import lombok.*;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "mechanics")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)

public class Mechanic extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private Specialization specialization;
    @Column(name = "still_work")
    @Convert(converter = YesNoBooleanConverter.class)
    @Builder.Default
    private boolean stillWork = Boolean.TRUE;
    @ManyToMany(mappedBy = "mechanicSet", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    private Set<Car> carSet;


}
