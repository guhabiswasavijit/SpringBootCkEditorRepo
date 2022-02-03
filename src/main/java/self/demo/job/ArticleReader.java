package self.demo.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Autowired;
import self.demo.model.MindWave;
import self.demo.repository.MindWaveRepo;

public class ArticleReader implements ItemReader<MindWave>,ItemStream{
    @Autowired
    private MindWaveRepo mindWaveRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
    @Override
    public MindWave read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return mindWaveRepo.findAllAddedArticle();
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        String articles = executionContext.getString("articleInserted");
        if(articles != null){
            try{
                MindWave article = this.read();
                executionContext.putString("articleInserted",article.getId().toString());
            }catch (Exception ex){
                LOGGER.error(ex.getMessage());
            }
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        String articles = executionContext.getString("articleInserted");
        try{
            MindWave article = this.read();
            StringBuilder articleStr = new StringBuilder(articles);
            articleStr.append(":");
            articleStr.append(article.getId());
            executionContext.putString("articleInserted",articleStr.toString());
        }catch (Exception ex){
            LOGGER.error(ex.getMessage());
        }
    }

    @Override
    public void close() throws ItemStreamException {
        LOGGER.info("Job done");
    }
}
