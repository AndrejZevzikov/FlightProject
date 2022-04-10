package entities;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hibernate.annotations.CascadeType.*;

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
    @Cascade(SAVE_UPDATE)
    @JoinColumn(name = "plane_id")
    private Plane plane;
    private Date flightTime;

    public String getFlightDateToString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return flightTime == null ? "" : formatter.format(flightTime);
    }

    @Override
    public String toString() {
        return "Id: " + getId() + " From: " + getLocationFrom() + " To: " + getLocationTo()
                + " Time: " + getFlightTime() + " Plane number " + getPlane().getNumber();
    }

}
