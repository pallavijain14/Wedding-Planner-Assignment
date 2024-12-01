package org.planner.clients;

import lombok.extern.slf4j.Slf4j;
import org.planner.checks.ResourceNotFoundException;
import org.planner.checks.ValidationException;
import org.planner.events.Event;
import org.planner.events.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EventRepository eventRepository;

    public Client registerClient(Client client) {
        if (client.getWeddingDate().isBefore(LocalDate.now())) {
            log.info("---in exception");
            throw new ValidationException("Wedding date cannot be in the past");
        }else{
            return clientRepository.save(client);
        }
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with ID: " + id));
    }

    public List<Client> getClients(LocalDate weddingDate, Double minBudget, Double maxBudget) {
        if (weddingDate != null) {
            return clientRepository.findByWeddingDate(weddingDate);
        } else if (minBudget != null && maxBudget != null) {
            return clientRepository.findByBudgetBetween(minBudget, maxBudget);
        } else {
            return clientRepository.findAll();
        }
    }




    public BigDecimal calculateTotalCost(Long clientId) {
        List<Event> events = eventRepository.findByClientId(clientId);

        return events.stream()
                .map(Event::getCost) // Assuming Event has a `cost` field
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
