package entities;

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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(unique = true,nullable = false)
    private String email;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<UserOrder> orders;
}
