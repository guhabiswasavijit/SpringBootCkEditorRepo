package self.demo.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import self.demo.model.MindWave;
import self.demo.solr.beans.Restaurant;

@Component("processor")
public class ArticleProcessor implements ItemProcessor<MindWave,Restaurant>{
    private static final Logger LOGGER = LoggerFactory.getLogger("JobLogger");
    @Override
    public Restaurant process(MindWave mindWave) throws Exception {
        LOGGER.info("Processing article {}",mindWave.getTitle());
        Restaurant restaurant = new Restaurant();
        BeanUtils.copyProperties(mindWave,restaurant,"content","lat","lng");
        return restaurant;
    }

}
