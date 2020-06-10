package car.service.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "service_requests")
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ServiceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Double amount;
    @CreationTimestamp
    @Column(name = "created_request_date")
    private LocalDateTime createdRequestDate;
    @Column(name = "repair_date")
    private LocalDateTime repairDate;
    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Car car;
    @Formula(value = "(select sum(cs.amount) from car_service.service_requests cs)")
    @ToString.Exclude
    private Double totalAmount;
    @Formula(value = "(select count(cs.repair_date) from car_service.service_requests cs " +
            "where cs.repair_date is not null)")
    @ToString.Exclude
    private int openServiceRequests;
    @Formula(value = "(select count(cs.repair_date) from car_service.service_requests cs " +
            "where cs.repair_date is not null)")
    @ToString.Exclude
    private int closedServiceRequests;


}
