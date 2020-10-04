package com.compasso.client.controllersTest;

import com.compasso.client.ClientApplication;
import com.compasso.client.dto.ClientDTO;
import com.compasso.client.repositories.ClientRepository;
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
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClientApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class ClientControllerTest {

    private static final String ERROR_LOADING_JSON = "Ocorreu um erro ao carregar arquivo json";
    private static final String BASE_TEST_RESOURCES_PATH = "src/test/resources/json/client/";
    private static final String BASE_ENDPOINT = "http://localhost:8080/api/client";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void getAllClients() {
        ResponseEntity<String> entity = restTemplate.getForEntity(BASE_ENDPOINT+"/search", String.class);
        Assertions.assertThat(entity.getBody())
                .isEqualTo(bytesFromPath("getAllClients.json"));
    }

    @Test
    public void getAllClientsByName() {
        ResponseEntity<String> entity = restTemplate.getForEntity(BASE_ENDPOINT+"/search-name/vini", String.class);
        Assertions.assertThat(entity.getBody())
                .isEqualTo(bytesFromPath("getAllClientsByName.json"));
    }

    @Test
    public void getAllClientsByNameNotFound() {
        ResponseEntity<String> entity = restTemplate.getForEntity(BASE_ENDPOINT+"/search-name/Robert", String.class);
        Assertions.assertThat(entity.getBody())
                .containsIgnoringCase(bytesFromPath("getAllClientsByNameNotFound.json"));
    }

    @Test
    public void getClientById() {
        ResponseEntity<String> entity = restTemplate.getForEntity(BASE_ENDPOINT+"/search/1", String.class);
        Assertions.assertThat(entity.getBody())
                .isEqualTo(bytesFromPath("getClientById.json"));
    }

    @Test
    public void createClient() {
        ResponseEntity<String> entity = restTemplate.postForEntity(BASE_ENDPOINT+"/create", getCreateClient(), String.class);
        Assertions.assertThat(entity.getBody())
                .isEqualTo(bytesFromPath("createClient.json"));
        clientRepository.deleteById(3L);
    }

    @Test
    public void createClientCityNotFound() {
        ResponseEntity<String> entity = restTemplate.postForEntity(BASE_ENDPOINT+"/create", getCreateClientCityNotFound(), String.class);
        Assertions.assertThat(entity.getBody())
                .containsIgnoringCase(bytesFromPath("createClientCityNotFound.json"));
    }

    public static String bytesFromPath(final String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(BASE_TEST_RESOURCES_PATH + path)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(ERROR_LOADING_JSON, e);
        }
    }

    public ClientDTO getCreateClient(){
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setBornDate(LocalDate.of(1995, 4, 22));
        clientDTO.setCityEntityId(4L);
        clientDTO.setFullName("Novo cadastro");
        clientDTO.setGender("M");
        return clientDTO;
    }

    public ClientDTO getCreateClientCityNotFound(){
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setBornDate(LocalDate.of(1995, 4, 22));
        clientDTO.setCityEntityId(7L);
        clientDTO.setFullName("Novo cadastro");
        clientDTO.setGender("M");
        return clientDTO;
    }
}
