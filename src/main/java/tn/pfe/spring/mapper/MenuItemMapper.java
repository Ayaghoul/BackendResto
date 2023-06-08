package tn.pfe.spring.mapper;

import org.mapstruct.Mapper;
import tn.pfe.spring.Controller.MenuItemController;
import tn.pfe.spring.DTO.MenuItemDTO;
import tn.pfe.spring.Entity.MenuItem;

@Mapper(componentModel = "spring", uses = {})
public interface MenuItemMapper extends IEntityMapper<MenuItemDTO, MenuItem> {

}