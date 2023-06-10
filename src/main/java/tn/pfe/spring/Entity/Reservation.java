package tn.pfe.spring.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;
import tn.pfe.spring.enumeration.ReservationStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private String time;
    @ManyToOne
    private AppUser user;
    private Long tableNumber;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
}
