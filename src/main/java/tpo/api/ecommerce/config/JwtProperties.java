package tpo.api.ecommerce.config;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component
public class JwtProperties {

    private String secretKeyString = "udHYtw5nlNTtS4tOZx7yoKIh8aIrANHZ";

}
