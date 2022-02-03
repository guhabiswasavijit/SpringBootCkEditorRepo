package self.demo.job;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.beans.BeanUtils;
import self.demo.model.MindWave;
import self.demo.solr.beans.Restaurant;


public class ArticleProcessor implements ItemProcessor<MindWave,Restaurant>, ItemStream {
    @Override
    public Restaurant process(MindWave mindWave) throws Exception {
        Restaurant restaurant = new Restaurant();
        BeanUtils.copyProperties(mindWave,restaurant,"content","lat","lng");
        return restaurant;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {

    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {

    }

    @Override
    public void close() throws ItemStreamException {

    }
}
