package org.planner.events;

import lombok.extern.slf4j.Slf4j;
import org.planner.checks.ResourceNotFoundException;
import org.planner.checks.ValidationException;
import org.planner.clients.Client;
import org.planner.clients.ClientRepository;
import org.planner.clients.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Service
@Slf4j
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    public Event addEvent(Event eventDto) {
        log.info("---"+LocalDate.now());
        Client client = clientRepository.findById(eventDto.getClient().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id " + eventDto.getClient().getId()));

        BigDecimal totalCost = clientService.calculateTotalCost(client.getId());
        BigDecimal newEventCost = eventDto.getCost(); // Assume the EventDto includes a cost field

        if (totalCost.add(newEventCost).compareTo(client.getBudget()) > 0) {
            throw new ValidationException("Client's budget will be exceeded with this booking.");
        }

        // Continue with saving the event
        Event event = new Event();
        event.setName(eventDto.getName());
        event.setDate(eventDto.getDate());
        event.setLocation(eventDto.getLocation());
        event.setCost(newEventCost);
        event.setClient(client);

        return eventRepository.save(event);
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + id));
    }


    public List<Event> getEvents(Boolean upcoming) {
        if (upcoming != null) {
            return upcoming ? eventRepository.findUpcomingEvents(LocalDate.now())
                    : eventRepository.findCompletedEvents(LocalDate.now());
        }
        return eventRepository.findAll();
    }
}
