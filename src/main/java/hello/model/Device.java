package hello.model;

import javax.persistence.*;

@Entity(name = "Device")
@Table(name = "device")
public class Device {

    @Id
    @GeneratedValue
    private Long id;

    private String macAddress;

    private String name;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "deviceType_id")
    private DeviceType type;
}
