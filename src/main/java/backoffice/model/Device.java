package backoffice.model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Device")
@Table(name = "device")
public class Device {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "macaddress")
    private String macAddress;

    @Column(name = "name")
    private String name;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "devicetype_id")
    private DeviceType deviceType;

    @ManyToMany(mappedBy = "userDevices")
    List<User> users;

    public Device () {

    }

    public Device(Long id, String macAddress, String name, DeviceType deviceType) {
        this.id = id;
        this.macAddress = macAddress;
        this.name = name;
        this.deviceType = deviceType;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }
}
