package com.compasso.client.services;

import com.compasso.client.dto.ClientDTO;
import com.compasso.client.entities.CityEntity;
import com.compasso.client.entities.ClientEntity;
import com.compasso.client.exceptions.CityExceptions;
import com.compasso.client.exceptions.ClientExceptions;
import com.compasso.client.repositories.CityRepository;
import com.compasso.client.repositories.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    private final CityRepository cityRepository;

    public ClientService(ClientRepository clientRepository, CityRepository cityRepository) {
        this.clientRepository = clientRepository;
        this.cityRepository = cityRepository;
    }

    public ClientEntity createClient(ClientDTO clientDTO) throws CityExceptions {
        Optional<CityEntity> cityEntity = cityRepository.findById(clientDTO.getCityEntityId());
        ClientEntity clientEntity = converterDtoToEntity(clientDTO);

        if(cityEntity.isPresent()){
            clientEntity.setCityEntity(cityEntity.get());
            clientEntity.setAge(getAge(clientEntity.getBornDate()));
            return clientRepository.save(clientEntity);
        } else {
            throw new CityExceptions("Cidade Não encontrada!");
        }
    }

    public ClientEntity updateNameClient(Long id, String fullName) throws ClientExceptions {
        if(StringUtils.isEmpty(id) || StringUtils.isEmpty(fullName)){
            throw new ClientExceptions("ID ou nome não informados!");
        } else {
            Optional<ClientEntity> clientEntity = clientRepository.findById(id);
            if(clientEntity.isPresent()){
                clientEntity.get().setFullName(fullName);
                return clientRepository.save(clientEntity.get());
            } else {
                throw new ClientExceptions("Cliente não encontrado!");
            }
        }
    }

    public List<ClientEntity> searchAll() {
        return clientRepository.findAllByOrderByFullName();
    }

    public List<ClientEntity> searchByFullName(String fullName) throws ClientExceptions {
        List<ClientEntity> clients = clientRepository.findAllByFullNameContainsIgnoreCaseOrderByFullName(fullName);

        if(clients.size() > 0){
            return clients;
        } else{
            throw new ClientExceptions("Nenhum cliente encontrado!");
        }
    }

    public Optional<ClientEntity> findById(Long id){
        return clientRepository.findById(id);
    }

    public void deleteClient(Long id) throws ClientExceptions {
        Optional<ClientEntity> clientEntity = clientRepository.findById(id);
        if(clientEntity.isPresent()){
            clientRepository.deleteById(id);
        } else {
            throw new ClientExceptions("Cliente não encontrado!");
        }

    }

    private static Long getAge(LocalDate bornDate) {
        return ChronoUnit.YEARS.between(bornDate, LocalDate.now());
    }

    private ClientEntity converterDtoToEntity(ClientDTO clientDTO){
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setFullName(clientDTO.getFullName().trim());
        clientEntity.setBornDate(clientDTO.bornDate);
        clientEntity.setGender(clientDTO.getGender().trim());
        return clientEntity;
    }

}
