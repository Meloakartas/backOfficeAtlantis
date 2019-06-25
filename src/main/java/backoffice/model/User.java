package backoffice.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "User")
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "useradid")
    private Long userADid;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "user_device",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "device_id")
    )
    private List<Device> userDevices = new ArrayList<>();

    public User() {

    }

    public User(Long id, Long userADid, String firstName, String lastName)
    {
        this.id = id;
        this.userADid = userADid;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserADid() {
        return userADid;
    }

    public void setUserADid(Long userADid) {
        this.userADid = userADid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String name(){
        return lastName + ' ' + firstName;
    }

    @Override
    public String toString() {
        return String.format("Customer[id=%d, userADid='%s', firstName='%s', lastName='%s']", id, userADid, firstName, lastName);
    }

    public List<Device> getUserDevices() {
        return userDevices;
    }

    public void setUserDevices(List<Device> userDevices) {
        this.userDevices = userDevices;
    }
}
