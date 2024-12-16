package ru.nsu.bookshop.controller;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import ru.nsu.bookshop.model.Client;
import ru.nsu.bookshop.service.ClientService;

@Controller
@RequestMapping("/clients")
@AllArgsConstructor
public class ClientViewController {
    private ClientService clientService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("clients", clientService.findAll());
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
