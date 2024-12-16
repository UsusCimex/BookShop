package ru.nsu.bookshop.controller;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import ru.nsu.bookshop.model.entity.Client;
import ru.nsu.bookshop.service.ClientService;

@Controller
@RequestMapping("/clients")
@AllArgsConstructor
public class ClientViewController {
    private ClientService clientService;

    @GetMapping
    public String getClients(@RequestParam(required = false) String firstName,
                             @RequestParam(required = false) String lastName,
                             @RequestParam(required = false) String email,
                             @RequestParam(required = false) String phone,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size,
                             Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Client> clients = clientService.searchClients(firstName, lastName, email, phone, pageable);

        model.addAttribute("clients", clients.getContent());
        model.addAttribute("currentPage", clients.getNumber());
        model.addAttribute("totalPages", clients.getTotalPages());
        model.addAttribute("totalItems", clients.getTotalElements());
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("email", email);
        model.addAttribute("phone", phone);
        return "client/clients";
    }

    @GetMapping("/add")
    public String addClientForm(Model model) {
        model.addAttribute("client", new Client());
        return "client/add-client";
    }

    @PostMapping("/add")
    public String addClient(@ModelAttribute Client client) {
        clientService.add(client);
        return "redirect:/clients";
    }

    @GetMapping("/edit/{id}")
    public String editClientForm(@PathVariable Long id, Model model) throws NotFoundException {
        Client client = clientService.findById(id).orElseThrow(() -> new NotFoundException());
        model.addAttribute("client", client);
        return "client/edit-client";
    }

    @PostMapping("/edit/{id}")
    public String updateClient(@PathVariable Long id, @ModelAttribute Client client) throws NotFoundException {
        clientService.update(id, client).orElseThrow(() -> new NotFoundException());
        return "redirect:/clients";
    }
}
