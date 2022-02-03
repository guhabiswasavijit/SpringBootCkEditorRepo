package self.demo.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Component;
import self.demo.repository.MindWaveRepo;
import self.demo.solr.beans.Restaurant;
import self.demo.solr.repository.SolrRestaurantRepository;

import java.time.Duration;
import java.util.List;
@Component("articleWriter")
public class ArticleWriter implements ItemWriter<Restaurant> {
    private static final Logger LOGGER = LoggerFactory.getLogger("JobLogger");
    @Autowired
    private SolrRestaurantRepository solrRepo;
    @Autowired
    private MindWaveRepo mindWaveRepo;


    @Override
    public void write(List<? extends Restaurant> restaurants) throws Exception {
        Iterable<Restaurant> indexedRestaurants = solrRepo.saveAll((Iterable<Restaurant>) restaurants, Duration.ZERO);
        LOGGER.info("Article uploaded to SOLR");
        indexedRestaurants.forEach(restaurant ->{
            mindWaveRepo.updateArticle(restaurant.getId());
            LOGGER.info("Article indexed successfully {}",restaurant.getId());
        });
    }
}
