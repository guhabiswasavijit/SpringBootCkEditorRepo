package self.demo.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@EqualsAndHashCode
@Getter
@Setter
@Entity
@Table(name="cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", length = 50, nullable = false, unique = false)
    private String name;
    @Column(name = "state_code", length = 50, nullable = false, unique = false)
    private String stateCode;
    @Column(name = "country_code", length = 50, nullable = false, unique = false)
    private String countryCode;
}
