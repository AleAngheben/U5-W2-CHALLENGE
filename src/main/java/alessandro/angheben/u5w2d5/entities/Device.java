package alessandro.angheben.u5w2d5.entities;

import alessandro.angheben.u5w2d5.enums.DeviceStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "devices")
public class Device {
@Id
@GeneratedValue
@Setter(AccessLevel.NONE)
    private UUID id;
    private String deviceType;
    @Enumerated(EnumType.STRING)
    private DeviceStatus status;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Device(String deviceType, DeviceStatus status) {
        this.deviceType = deviceType;
        this.status = status;
    }
}
