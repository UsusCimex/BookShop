package ru.nsu.bookshop.controller.rest;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.nsu.bookshop.model.entity.Client;
import ru.nsu.bookshop.service.ClientService;

@RestController
@RequestMapping("/api/clients")
@AllArgsConstructor
public class ClientRestController {
    private final ClientService clientService;

    @GetMapping
    public Page<Client> getClients(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return clientService.searchClients(firstName, lastName, email, phone, pageable);
    }

    @GetMapping("/all")
    public List<Client> findAll() {
        return clientService.findAll();
    }

    @PostMapping
    public void addClient(@RequestBody Client client) {
        clientService.add(client);
    }

    @PutMapping("/{id}")
    public void updateClient(@PathVariable Long id, @RequestBody Client client) throws Exception {
        clientService.update(id, client).orElseThrow(() -> new Exception("Client not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteById(id);
    }
}
