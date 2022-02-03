package self.demo.controller;

import com.google.gson.Gson;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import self.demo.dto.MindWaveDTO;
import self.demo.model.*;
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
    public ResponseEntity<?> fetchCities(@RequestParam("state") String stateCode,@RequestParam("country") String countryCode) {
        Response response = new Response();
        List<City> cities = cityRepo.findByCountryState(stateCode,countryCode);
        String jsonString = new Gson().toJson(cities);
        response.setData(jsonString);
        return ResponseEntity.ok(response);
    }
    @GetMapping(value = {"/fetchStates/{country}"})
    public ResponseEntity<?> fetchStates(@PathVariable String country) {
        Response response = new Response();
        List<State> states = stateRepo.findByCountry(country);
        String jsonString = new Gson().toJson(states);
        response.setData(jsonString);
        return ResponseEntity.ok(response);
    }
    @GetMapping(value = {"/fetchCountries/{region}"})
    public ResponseEntity<?> fetchCountries(@PathVariable String region) {
        Response response = new Response();
        List<Country> countries = countryRepo.findByRegion(region);
        String jsonString = new Gson().toJson(countries);
        response.setData(jsonString);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = {"/saveContent"})
    public ResponseEntity<?> saveArticle(@RequestBody MindWaveDTO dto) {
        Response response = new Response();
        MindWave entity = new MindWave();
        BeanUtils.copyProperties(dto,entity,"id");
        MindWave save = mindWaveRepo.save(entity);
        response.setData(save.getTitle());
        return ResponseEntity.ok(response);
    }


}