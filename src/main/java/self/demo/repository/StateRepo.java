package self.demo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import self.demo.model.State;
import java.util.List;
@Repository("stateRepo")
public interface StateRepo extends CrudRepository<State, Integer> {
    @Query("SELECT st FROM State st WHERE LOWER(st.countryCode) = LOWER(:countryCode)")
    List<State> findByCountry(@Param("countryCode") String countryCode);
}
