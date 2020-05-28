package car.service.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass

public class Person {

    private String name;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone_number", nullable = false)
    // todo limit number length
    private int phoneNumber;

}
