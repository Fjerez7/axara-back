package com.axara.backend.controllers;

import com.axara.backend.models.ClientModel;
import com.axara.backend.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5174")
@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ArrayList<ClientModel>getClients(){
        return this.clientService.getClients();
    }


    @GetMapping(path = "/{id}")
    public Optional<ClientModel> getClient(@PathVariable("id") Long id) {
        return this.clientService.getClientById(id);
    }


    @PostMapping
    public ClientModel saveClient(@RequestBody ClientModel client){
        return this.clientService.saveClient(client);
    }


    @PutMapping(path = "/{id}")
    public ClientModel updateById(@RequestBody ClientModel request, @PathVariable("id") Long id){
        return this.clientService.updateById(request,id);
    }


    @DeleteMapping(path = "/{id}")
    public String DeleteClient(@PathVariable("id") Long id){
        boolean ok = this.clientService.deleteClient(id);

        if (ok){
            return "Client Deleted";
        }else {
            return "Error in the process, can't delete client";
        }
    }
}
