package entities;

import enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;
    private Status status;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "flightOrder_passenger")
    private List<Passenger> passengers;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "flightOrder_flights")
    private List<FlightSchedule> flights;

}
