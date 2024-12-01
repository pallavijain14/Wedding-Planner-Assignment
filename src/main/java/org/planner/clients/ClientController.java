package org.planner.clients;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<Client> registerClient(@RequestBody Client client) {
        Client savedClient = clientService.registerClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Client client = clientService.getClientById(id);
        return ResponseEntity.ok(client);
    }


    @GetMapping
    public ResponseEntity<List<Client>> getClients(
            @RequestParam(required = false) LocalDate weddingDate,
            @RequestParam(required = false) Double minBudget,
            @RequestParam(required = false) Double maxBudget) {
        List<Client> clients = clientService.getClients(weddingDate, minBudget, maxBudget);
        return ResponseEntity.ok(clients);
    }



}
