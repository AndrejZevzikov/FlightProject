package entities;

import enums.Status;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.annotations.CascadeType.SAVE_UPDATE;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    private Status status;
    @OneToMany(mappedBy = "userOrder",cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @Cascade(SAVE_UPDATE)
    private List<Ticket> tickets = new ArrayList<>();

    public void addTicket(Ticket ticket){
        ticket.setUserOrder(this);
        tickets.add(ticket); //TODO supaprastinti visur
    }
}