package com.compasso.client.controllers;

import com.compasso.client.entities.CityEntity;
import com.compasso.client.exceptions.CityExceptions;
import com.compasso.client.services.CityService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/city")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @ApiOperation(value = "Cadastro de uma cidade")
    @PostMapping("/create")
    public CityEntity create(@Valid @RequestBody CityEntity cityEntity) throws Exception {
        return cityService.createCity(cityEntity);
    }

    @ApiOperation(value = "Busca as cidades pelo nome em ordem alfabética")
    @GetMapping("/search-name/{name}")
    public List<CityEntity> searchByName(@Valid @PathVariable(name = "name") String name) throws CityExceptions {
        return cityService.searchByName(name);
    }

    @ApiOperation(value = "Busca as cidades pelo estado em ordem alfabética")
    @GetMapping("/search-state/{state}")
    public List<CityEntity> searchByState(@Valid @PathVariable(name = "state") String state) throws CityExceptions {
        return cityService.searchByState(state);
    }

    @ApiOperation(value = "Busca todas as cidades em ordem alfabética")
    @GetMapping("/search")
    public List<CityEntity> searchAll() {
        return cityService.searchAll();
    }
}
