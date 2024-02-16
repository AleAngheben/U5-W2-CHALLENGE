package alessandro.angheben.u5w2d5.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record NewEmployeeDTO(
        @NotEmpty
        @Size(min = 5, max = 18, message = "Username deve essere almeno di 5 caratteri e non più di 18")
        String username,
        @NotEmpty(message = "inserisci il nome")
        @Size(min = 4, max = 15, message = "il nome deve essere di almeno 4 caratteri e massimo di 15")
        String firstName,
        @NotEmpty(message = "inserisci il cognome")
        @Size(min = 4, max = 15, message = "il cognome deve essere di almeno 4 caratteri e massimo di 15")
        String lastName,
        @NotEmpty(message = "inserisci l'email")
        @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "L'email inserita non è valida")
        String email


) {


}
