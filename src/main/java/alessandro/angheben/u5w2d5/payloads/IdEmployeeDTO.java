package alessandro.angheben.u5w2d5.payloads;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record IdEmployeeDTO(

        @NotEmpty(message = "insersci l'id !!")
        //@Id
        UUID id
) {


}
