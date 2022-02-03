package self.demo.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import self.demo.model.MindWave;
import self.demo.repository.MindWaveRepo;

import java.util.LinkedList;
@Component("reader")
public class ArticleReader implements ItemReader<MindWave>,ItemStream{
    @Autowired
    private MindWaveRepo mindWaveRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger("JobLogger");
    private final LinkedList<Integer> idList = new LinkedList<Integer>();
    @Override
    public MindWave read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return mindWaveRepo.findById(idList.pop()).orElse(null);
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        LOGGER.info("Started index upload job");
        idList.addAll(mindWaveRepo.findAllAddedArticle());
        String articles = executionContext.getString("articleInserted");
        if(articles != null){
            try{
                MindWave article = this.read();
                LOGGER.info("Processing article {}",article.getTitle());
                executionContext.putString("articleInserted",article.getId().toString());
            }catch (Exception ex){
                LOGGER.error(ex.getMessage());
            }
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        String articles = executionContext.getString("articleInserted");
        LOGGER.info("Articles at execution context {}",articles);
        try{
            MindWave article = this.read();
            StringBuilder articleStr = new StringBuilder(articles);
            articleStr.append(":");
            articleStr.append(article.getId());
            LOGGER.info("Articles updated at execution context {}",articleStr.toString());
            executionContext.putString("articleInserted",articleStr.toString());
        }catch (Exception ex){
            LOGGER.error(ex.getMessage());
        }
    }

    @Override
    public void close() throws ItemStreamException {
        idList.clear();
        LOGGER.info("Job done");
    }
}
