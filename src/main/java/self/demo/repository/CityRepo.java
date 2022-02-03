package self.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import self.demo.model.City;
import java.util.List;
@Repository("cityRepo")
public interface CityRepo extends CrudRepository<City, Integer> {
    @Query("SELECT ct FROM City ct WHERE LOWER(ct.stateCode) = LOWER(:stateCode) AND LOWER(ct.countryCode) = LOWER(:countryCode)")
    List<City> findByCountryState(@Param("stateCode") String stateCode,@Param("countryCode") String countryCode);
}

