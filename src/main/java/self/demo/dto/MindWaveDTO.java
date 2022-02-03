package self.demo.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
public class MindWaveDTO {
    private String title;
    private String name;
    private Double lat ;
    private Double lng ;
    private String city;
    private String state;
    private String country;
    private String content;
}
