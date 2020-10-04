package com.compasso.client.controllers;

import com.compasso.client.dto.ClientDTO;
import com.compasso.client.entities.ClientEntity;
import com.compasso.client.exceptions.ClientExceptions;
import com.compasso.client.services.ClientService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @ApiOperation(value = "Cadastro de um cliente")
    @PostMapping("/create")
    public ClientEntity create(@Valid @RequestBody ClientDTO clientDTO) throws Exception {
        return clientService.createClient(clientDTO);
    }

    @ApiOperation(value = "Atualiza o nome do Cliente")
    @PutMapping("/update")
    public ClientEntity updateName(@RequestParam(name = "id") Long id, @RequestParam(name = "fullName") String fullName) throws ClientExceptions {
        return clientService.updateNameClient(id, fullName);
    }

    @ApiOperation(value = "Busca os clientes pelo nome em ordem alfabética")
    @GetMapping("/search-name/{fullName}")
    public List<ClientEntity> searchByName(@PathVariable(name = "fullName") String fullName) throws ClientExceptions {
        return clientService.searchByFullName(fullName);
    }

    @ApiOperation(value = "Busca as clientes pelo id")
    @GetMapping("/search/{id}")
    public Optional<ClientEntity> searchByState(@PathVariable(name = "id") Long id) {
        return clientService.findById(id);
    }

    @ApiOperation(value = "Busca todos os clientes em ordem alfabética")
    @GetMapping("/search")
    public List<ClientEntity> searchAll() {
        return clientService.searchAll();
    }

    @ApiOperation(value = "Deleta um Cliente")
    @DeleteMapping("/delete/{id}")
    public void deleteClient(@PathVariable("id") Long id) throws ClientExceptions {
        clientService.deleteClient(id);
    }
}
