package alessandro.angheben.u5w2d5.exceptions.payloads;

import java.util.Date;
import java.util.List;

public record ErrorsPayloadWithList (String message,
                                     Date timestamp,
                                     List<String> errorsList){
}
