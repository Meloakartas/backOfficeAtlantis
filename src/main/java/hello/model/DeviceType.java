package hello.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "deviceType")
@Table(name = "deviceType")
public class DeviceType {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
