package entities;

import enums.Status;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.annotations.CascadeType.MERGE;
import static org.hibernate.annotations.CascadeType.SAVE_UPDATE;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    private Status status;
    @OneToMany(mappedBy = "flightOrder",cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<OrderedFlights> orderedFlights = new ArrayList<>();

    public void addOrderedFlight(OrderedFlights orderedFlight){
        orderedFlight.setFlightOrder(this);
        orderedFlights.add(orderedFlight); //TODO supaprastinti visur
    }

}