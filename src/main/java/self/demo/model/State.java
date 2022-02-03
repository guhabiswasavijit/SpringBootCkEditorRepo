package self.demo.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name="states")
@EqualsAndHashCode
@Getter
@Setter
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "iso2", length = 50, nullable = true, unique = false)
    private String stateCode;
    @Column(name = "name", length = 50, nullable = false, unique = false)
    private String stateName;
    @Column(name = "country_code", length = 50, nullable = false, unique = false)
    private String countryCode;
}
