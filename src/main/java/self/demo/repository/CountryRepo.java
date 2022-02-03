package self.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import self.demo.model.Country;

import java.util.List;

@Repository
public interface CountryRepo extends JpaRepository<Country, Integer> {
    @Query("SELECT ct FROM Country ct WHERE LOWER(mv.region) = LOWER(:region)")
    List<Country> findByRegion(@Param("region") String region);
}
