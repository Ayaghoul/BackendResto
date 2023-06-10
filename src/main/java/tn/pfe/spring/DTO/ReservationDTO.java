package tn.pfe.spring.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {

    private LocalDateTime date;
    private String time;
    private Long userId;
    private Long tableNumber;
}
