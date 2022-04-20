package entities;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hibernate.annotations.CascadeType.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String locationFrom;
    private String locationTo;
    private Date flightTime;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @Cascade(SAVE_UPDATE)
    @JoinColumn(name = "plane_id")
    private Plane plane;

    public String getFlightDateToString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return flightTime == null ? "" : formatter.format(flightTime);
    }

    @Override
    public String toString() {
        return "Id: " + getId() + " From: " + getLocationFrom() + " To: " + getLocationTo()
                + " Time: " + this.getFlightTime() + " Plane number " + getPlane().getNumber();
    }
}