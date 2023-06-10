package tn.pfe.spring.Service;

import tn.pfe.spring.DTO.ReservationDTO;
import tn.pfe.spring.Entity.Reservation;
import tn.pfe.spring.Entity.MenuItem;

import java.util.List;

public interface ReservationService {


    List<Reservation> getAllReservations();
    Reservation getReservationById(Long id);
    List<Reservation> getReservationByUserId();
    Reservation createReservation(ReservationDTO reservation);
    Reservation changeReservationStatus(Long id, String status);
    Reservation changeReservationTable(Long id, Long table);
    Reservation updateReservation(Long id, Reservation reservation);
    void deleteReservation(Long id);


}
