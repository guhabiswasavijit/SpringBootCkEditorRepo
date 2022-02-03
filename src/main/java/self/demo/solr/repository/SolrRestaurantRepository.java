package self.demo.solr.repository;

import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;
import self.demo.solr.beans.Restaurant;

import java.util.List;
@Repository("solrRepo")
public interface SolrRestaurantRepository extends SolrCrudRepository<Restaurant, Long> {

    List<Restaurant> findByName(String name);
}
