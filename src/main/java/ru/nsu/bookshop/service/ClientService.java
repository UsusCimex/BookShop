package ru.nsu.bookshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import ru.nsu.bookshop.model.entity.Client;
import ru.nsu.bookshop.repositrory.ClientRepository;

@Service
@AllArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public Page<Client> searchClients(String firstName, String lastName, String email, String phone, Pageable pageable) {
        return clientRepository.searchClients(firstName, lastName, email, phone, pageable);
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    public Client add(Client client) {
        return clientRepository.save(client);
    }

    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    public Optional<Client> update(Long id, Client client) {
        return clientRepository.findById(id)
            .map(clientToUpdate -> {
                clientToUpdate.setFirstName(client.getFirstName());
                clientToUpdate.setLastName(client.getLastName());
                clientToUpdate.setEmail(client.getEmail());
                clientToUpdate.setPhone(client.getPhone());
                return clientRepository.save(clientToUpdate);
            });
    }
}
