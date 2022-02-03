package self.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import self.demo.model.City;
import java.util.List;

public interface CityRepo extends JpaRepository<City, Integer> {
    @Query("SELECT ct FROM City ct WHERE LOWER(ct.state_code) = LOWER(:stateCode) AND LOWER(ct.country_code) = LOWER(:countryCode)")
    List<City> findByCountryState(@Param("stateCode") String stateCode,@Param("countryCode") String countryCode);
}

