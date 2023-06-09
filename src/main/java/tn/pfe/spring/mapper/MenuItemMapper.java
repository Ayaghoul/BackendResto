package tn.pfe.spring.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import tn.pfe.spring.Controller.MenuItemController;
import tn.pfe.spring.DTO.MenuItemDTO;
import tn.pfe.spring.Entity.MenuItem;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MenuItemMapper extends IEntityMapper<MenuItemDTO, MenuItem> {

}