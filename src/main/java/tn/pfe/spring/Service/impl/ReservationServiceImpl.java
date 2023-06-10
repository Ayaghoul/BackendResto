package tn.pfe.spring.Service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tn.pfe.spring.DTO.ReservationDTO;
import tn.pfe.spring.Entity.AppUser;
import tn.pfe.spring.Entity.Reservation;
import tn.pfe.spring.Entity.MenuItem;
import tn.pfe.spring.Repository.ReservationRepository;
import tn.pfe.spring.Repository.MenuItemRepository;
import tn.pfe.spring.Repository.UserRepository;
import tn.pfe.spring.Service.ReservationService;
import tn.pfe.spring.enumeration.ReservationStatus;
import tn.pfe.spring.mapper.ReservationMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ReservationMapper reservationMapper;


    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }


    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public List<Reservation> getReservationByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        AppUser user = userRepository.findByUsername(currentPrincipalName);
        LocalDateTime currentDate = LocalDateTime.now();
        List<Reservation> reservations = reservationRepository.findAllByUser(user);
        return reservations;
    }

    public Reservation createReservation(ReservationDTO reservationDTO) {
        AppUser userToUpdate = userRepository.findById(reservationDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("L'utilisateur avec l'ID " + reservationDTO.getUserId() + " n'a pas été trouvée."));
        Reservation reservation = new Reservation();
        reservation.setUser(userToUpdate);
        reservation.setDate(reservationDTO.getDate());
        reservation.setTime(reservationDTO.getTime());
        reservation.setStatus(ReservationStatus.Pending);
        return reservationRepository.save(reservation);
    }

    public Reservation changeReservationStatus(Long id, String status) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La reservation avec l'ID " + id + " n'a pas été trouvée."));
        reservation.setStatus(ReservationStatus.valueOf(status));
        return reservationRepository.save(reservation);
    }

    public Reservation changeReservationTable(Long id, Long table) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La reservation avec l'ID " + id + " n'a pas été trouvée."));
        reservation.setTableNumber(table);
        return reservationRepository.save(reservation);
    }


    public Reservation updateReservation(Long id, Reservation reservation) {
        Reservation reservationToUpdate = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La catégorie avec l'ID " + id + " n'a pas été trouvée."));
        return reservationRepository.save(reservationToUpdate);
    }


    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La catégorie avec l'ID " + id + " n'a pas été trouvée."));
        reservationRepository.deleteById(id);
    }
}