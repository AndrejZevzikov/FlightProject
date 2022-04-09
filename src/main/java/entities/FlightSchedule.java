package entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String locationFrom;
    private String locationTo;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "plane_id")
    private Plane plane;
    private Date flightTime;

    public String getFlightDateToString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return flightTime == null ? "" : formatter.format(flightTime);
    }

    @Override
    public String toString() {
        return "Id: " + getId() + " From: " + getLocationFrom() + " To: " + getLocationTo()
                + " Time: " + getFlightTime() + " Plane number " + getPlane().getNumber();
    }
}
