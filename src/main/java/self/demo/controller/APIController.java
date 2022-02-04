package self.demo.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import self.demo.dto.MindWaveDTO;
import self.demo.model.City;
import self.demo.model.Country;
import self.demo.model.MindWave;
import self.demo.model.State;
import self.demo.repository.CityRepo;
import self.demo.repository.CountryRepo;
import self.demo.repository.MindWaveRepo;
import self.demo.repository.StateRepo;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class APIController {
    @Autowired
    private CountryRepo countryRepo;
    @Autowired
    private MindWaveRepo mindWaveRepo;
    @Autowired
    private StateRepo stateRepo;
    @Autowired
    private CityRepo cityRepo;
    @GetMapping(value = {"/fetchCities"})
    public List<City> fetchCities(@RequestParam("state") String stateCode,@RequestParam("country") String countryCode) {
        List<City> cities = cityRepo.findByCountryState(stateCode,countryCode);
        return cities;
    }
    @GetMapping(value = {"/fetchStates"})
    public List<State> fetchStates(@RequestParam("country") String country) {
        List<State> states = stateRepo.findByCountry(country);
        return states;
    }
    @GetMapping(value = {"/fetchCountries"})
    public List<Country> fetchCountries(@RequestParam("region") String region) {
        List<Country> countries = countryRepo.findByRegion(region);
        return countries;
    }

    @PostMapping(value = {"/saveContent"},consumes = "application/json")
    public MindWave saveArticle(@RequestBody MindWaveDTO dto) {
        MindWave entity = new MindWave();
        BeanUtils.copyProperties(dto,entity,"id");
        MindWave savedArticle = mindWaveRepo.save(entity);
        return savedArticle;
    }
}