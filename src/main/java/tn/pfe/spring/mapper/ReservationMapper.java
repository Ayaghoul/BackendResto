package tn.pfe.spring.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import tn.pfe.spring.DTO.ReservationDTO;
import tn.pfe.spring.Entity.Reservation;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ReservationMapper  {

    Reservation toEntity(ReservationDTO reservationDTO);
}