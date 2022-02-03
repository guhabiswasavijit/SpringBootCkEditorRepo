package self.demo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import self.demo.model.MindWave;

import java.util.List;


@Repository("mindWaveRepo")
public interface MindWaveRepo extends CrudRepository<MindWave, Integer> {
    @Query("SELECT mv FROM MindWave mv WHERE mv.id=:articleId")
    MindWave findById(@Param("articleId") String articleId);
    @Query("SELECT mv FROM MindWave mv WHERE LOWER(mv.title) = LOWER(:articleTitle)")
    MindWave findByArticleTitle(@Param("articleTitle") String articleTitle);
    @Query(value = "SELECT id FROM MindWave mv WHERE mv.IS_IMPORTED=0",nativeQuery = true)
    List<Integer> findAllAddedArticle();
    @Query(value = "UPDATE MindWave mv SET mv.IS_IMPORTED=1 WHERE id=:articleId",nativeQuery = true)
    boolean updateArticle(@Param("articleId") Long articleId);
}
