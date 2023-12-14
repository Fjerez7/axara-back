package com.axara.backend.services;

import com.axara.backend.models.ClientModel;
import com.axara.backend.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ClientService {

    ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ArrayList<ClientModel> getClients(){
        return (ArrayList<ClientModel>) clientRepository.findAll();
    }

    public Optional<ClientModel> getClientById(Long id){
        return clientRepository.findById(id);
    }

    public ClientModel saveClient(ClientModel client){
        return clientRepository.save(client);
    }

    public ClientModel updateById (ClientModel request, Long id){
        ClientModel client = clientRepository.findById(id).get();
        System.out.println(client);
        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setEmail(request.getEmail());

        return client;
    }

    public Boolean deleteClient (Long id) {
        try {
            clientRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
