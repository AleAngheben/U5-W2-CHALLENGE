package alessandro.angheben.u5w2d5.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;


public record NewDeviceDTO(

        @NotEmpty(message = "Inserisci il tipo!!")
        @NotBlank
        String type
) {
}
