
package tn.pfe.spring.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.pfe.spring.DTO.ReservationDTO;
import tn.pfe.spring.Entity.Reservation;
import tn.pfe.spring.Service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/get-all-reservations") //tested successfully
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/get-reservation--by-id/{id}") //tested successfully
    public Reservation getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id);
    }

    @GetMapping("/get-reservation-by-user-id") //tested successfully
    public ResponseEntity<List<Reservation>> getReservationByUserId() {
        return ResponseEntity.ok(reservationService.getReservationByUserId());
    }
    

    @PostMapping("/add-reservation") //tested successfully
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDTO reservation) {
        return ResponseEntity.ok().body(reservationService.createReservation(reservation));
    }
    @PatchMapping("/change-reservation-status/{id}") //tested successfully
    public ResponseEntity<Reservation> createReservation(@PathVariable("id") Long id, @RequestBody String status) {
        return ResponseEntity.ok().body(reservationService.changeReservationStatus(id, status));
    }

    @PatchMapping("/change-reservation-table/{id}") //tested successfully
    public ResponseEntity<Reservation> createReservation(@PathVariable("id") Long id, @RequestBody Long table) {
        return ResponseEntity.ok().body(reservationService.changeReservationTable(id, table));
    }

    @PatchMapping("/update-reservation/{id}")//tested successfully
    public Reservation updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        return reservationService.updateReservation(id, reservation);
    }

    @DeleteMapping("/delete-reservation/{id}")//tested successfully
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
    }
    
    // removeMenuItemFromReservation
}
