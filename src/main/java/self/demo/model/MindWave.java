package self.demo.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="mind_wave")
@EqualsAndHashCode
@Getter
@Setter
public class MindWave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", length = 60, nullable = false, unique = true)
    private String title;
    @Column(name = "name", length = 60, nullable = false, unique = false)
    private String name;
    @Column(name = "lat",nullable = false, unique = false)
    private Double lat ;
    @Column(name = "lng",nullable = false, unique = false)
    private Double lng ;
    @Column(name = "city", length = 60, nullable = false, unique = false)
    private String city;
    @Column(name = "state", length = 60, nullable = false, unique = false)
    private String state;
    @Column(name = "country", length = 60, nullable = false, unique = false)
    private String country;
    @Column(name = "content", length = 1000, nullable = false, unique = false)
    private String content;
}
