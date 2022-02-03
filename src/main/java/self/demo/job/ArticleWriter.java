package self.demo.job;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.repository.SolrCrudRepository;
import self.demo.repository.MindWaveRepo;
import self.demo.solr.beans.Restaurant;

import java.time.Duration;
import java.util.List;

public class ArticleWriter implements ItemWriter<Restaurant> {
    @Autowired
    private SolrCrudRepository solrRepo;
    @Autowired
    private MindWaveRepo mindWaveRepo;


    @Override
    public void write(List<? extends Restaurant> restaurants) throws Exception {
        Iterable<Restaurant> indexedRestaurants = solrRepo.saveAll(restaurants, Duration.ZERO);
        indexedRestaurants.forEach(restaurant ->{
            mindWaveRepo.updateArticle(restaurant.getId());
        });
    }
}
