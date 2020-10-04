package com.compasso.client.services;

import com.compasso.client.entities.CityEntity;
import com.compasso.client.exceptions.CityExceptions;
import com.compasso.client.repositories.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public CityEntity createCity(CityEntity cityEntity) throws CityExceptions {
        CityEntity city = cityRepository.findOneByNameIgnoreCase(cityEntity.getName());

        if(Objects.nonNull(city)){
            throw new CityExceptions("Cidade já cadastrada!");
        }

        return cityRepository.save(cityEntity);
    }

    public List<CityEntity> searchByName(String name) throws CityExceptions {
        List<CityEntity> cities = cityRepository.findByNameContainingIgnoreCaseOrderByName(name);

        if(cities.size() > 0){
            return cities;
        } else {
            throw new CityExceptions("Nenhuma cidade encontrada!");
        }
    }

    public List<CityEntity> searchByState(String state) throws CityExceptions {
        List<CityEntity> cities = cityRepository.findByStateContainingIgnoreCaseOrderByName(state);

        if(cities.size() > 0){
            return cities;
        } else {
            throw new CityExceptions("Nenhuma cidade encontrada!");
        }
    }

    public List<CityEntity> searchAll() {
        return cityRepository.findAllByOrderByName();
    }
}
