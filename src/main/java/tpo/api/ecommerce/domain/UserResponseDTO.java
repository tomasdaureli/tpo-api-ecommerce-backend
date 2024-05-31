package tpo.api.ecommerce.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tpo.api.ecommerce.entity.UserRoles;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserResponseDTO {

    private Long id;

    private String name;

    private String lastName;

    private String email;

    private UserRoles role;

    private List<BuyDTO> buys;

}
