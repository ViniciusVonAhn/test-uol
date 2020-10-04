package com.compasso.client.controllersTest;

import com.compasso.client.ClientApplication;
import com.compasso.client.entities.CityEntity;
import com.compasso.client.repositories.CityRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClientApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class CityControllerTest {

    private static final String ERROR_LOADING_JSON = "Ocorreu um erro ao carregar arquivo json";
    private static final String BASE_TEST_RESOURCES_PATH = "src/test/resources/json/city/";
    private static final String BASE_ENDPOINT = "http://localhost:8080/api/city";

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private CityRepository cityRepository;

    @Test
    public void getAllCities() {
        ResponseEntity<String> entity = restTemplate.getForEntity(BASE_ENDPOINT+"/search", String.class);
        Assertions.assertThat(entity.getBody()).isEqualTo(bytesFromPath("getAllCities.json"));
    }

    @Test
    public void getAllCitiesByName() {
        ResponseEntity<String> entity = restTemplate.getForEntity(BASE_ENDPOINT+"/search-name/Pelotas", String.class);
        Assertions.assertThat(entity.getBody())
                .isEqualTo(bytesFromPath("getAllCitiesByName.json"));
    }

    @Test
    public void getAllCitiesByNameNotFound() {
        ResponseEntity<String> entity = restTemplate.getForEntity(BASE_ENDPOINT+"/search-name/Brasilia", String.class);
        Assertions.assertThat(entity.getBody())
                .containsIgnoringCase(bytesFromPath("getAllCitiesByNameNotFound.json"));
    }

    @Test
    public void getAllCitiesByState() {
        ResponseEntity<String> entity = restTemplate.getForEntity(BASE_ENDPOINT+"/search-state/RJ", String.class);
        Assertions.assertThat(entity.getBody())
                .isEqualTo(bytesFromPath("getAllCitiesByState.json"));
    }

    @Test
    public void getAllCitiesByStateNotFound() {
        ResponseEntity<String> entity = restTemplate.getForEntity(BASE_ENDPOINT+"/search-state/BA", String.class);
        Assertions.assertThat(entity.getBody())
                .containsIgnoringCase(bytesFromPath("getAllCitiesByNameNotFound.json"));
    }

    @Test
    public void createCity() {
        ResponseEntity<String> entity = restTemplate.postForEntity(BASE_ENDPOINT+"/create", getCreateCityEntity(), String.class);
        Assertions.assertThat(entity.getBody()).isEqualTo(bytesFromPath("createCity.json"));
        cityRepository.deleteById(5L);
    }

    @Test
    public void createCityNotFound() {
        ResponseEntity<String> entity = restTemplate.postForEntity(BASE_ENDPOINT+"/create", getCreateCityEntityExists(), String.class);
        Assertions.assertThat(entity.getBody()).containsIgnoringCase(bytesFromPath("createCityNotFound.json"));
    }

    public CityEntity getCreateCityEntityExists(){
        CityEntity cityEntity = new CityEntity();
        cityEntity.setName("Pelotas");
        cityEntity.setState("RS");
        return cityEntity;
    }

    public CityEntity getCreateCityEntity(){
        CityEntity cityEntity = new CityEntity();
        cityEntity.setName("Porto Alegre");
        cityEntity.setState("RS");
        return cityEntity;
    }

    public static String bytesFromPath(final String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(BASE_TEST_RESOURCES_PATH + path)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(ERROR_LOADING_JSON, e);
        }
    }
}
