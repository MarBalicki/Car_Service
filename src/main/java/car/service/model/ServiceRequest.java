package car.service.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
//    @UpdateTimestamp
    @Column(name = "repair_date")
    private LocalDateTime repairDate;
    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Car car;

}
