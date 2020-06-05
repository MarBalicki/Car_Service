package car.service.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "owners")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)

public class Owner extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
