package entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

import static org.hibernate.annotations.CascadeType.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderedFlights {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    private FlightSchedule flightSchedule;
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @Cascade(SAVE_UPDATE)
    private Passenger passenger;
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id",referencedColumnName = "id")
    private FlightOrder flightOrder;

}
