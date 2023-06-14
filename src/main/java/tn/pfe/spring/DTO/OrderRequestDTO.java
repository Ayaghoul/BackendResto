package tn.pfe.spring.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {
    private String firstName;
    private String lastName;
    private String middleName;
    private String company;
    private String email;
    private String phone;
    private String country;
    private String city;
    private String place;
    private String postalCode;
    private String address;
    private String cartId;
}
