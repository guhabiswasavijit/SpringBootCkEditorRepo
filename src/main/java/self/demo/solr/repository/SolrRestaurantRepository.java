package self.demo.solr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.repository.*;
import self.demo.solr.beans.Restaurant;
import java.util.List;

public interface SolrRestaurantRepository extends SolrCrudRepository<Restaurant, Long> {

    List<Restaurant> findByName(String name);

    Page<Restaurant> findByNameOrDescription(@Boost(2) String name, String description, Pageable pageable);

    @Query("name:?0")
    @Facet(fields = { "categories_txt" }, limit = 5)
    FacetPage<Restaurant> findByNameAndFacetOnCategories(String name, Pageable page);

    @Highlight(prefix = "<highlight>", postfix = "</highlight>")
    HighlightPage<Restaurant> findByDescription(String description, Pageable pageable);


}
