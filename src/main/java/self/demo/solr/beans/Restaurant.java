package self.demo.solr.beans;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.solr.client.solrj.beans.Field;
@EqualsAndHashCode
@Getter
@Setter
public class Restaurant {
    @Field
    private Long id;
    @Field
    private String title;
    @Field
    private String name;
    @Field
    private String city;
    @Field
    private String state;
    @Field
    private String country;
}
