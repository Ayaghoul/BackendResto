package tn.pfe.spring.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.pfe.spring.Entity.AppUser;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavorisResponse {

    AppUser user;
    Long lengthFavorites;
}
