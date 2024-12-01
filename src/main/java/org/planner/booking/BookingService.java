package org.planner.booking;

import org.planner.checks.ResourceNotFoundException;
import org.planner.checks.ValidationException;
import org.planner.events.Event;
import org.planner.events.EventRepository;
import org.planner.vendors.Vendor;
import org.planner.vendors.VendorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    @Autowired
    private VendorsRepository vendorRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EventRepository eventRepository;

    public Booking bookVendor(Long vendorId, Long eventId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with id " + vendorId));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId));

        if (!vendor.getAvailableDates().contains(event.getDate())) {
            throw new ValidationException("Vendor is not available on the selected date.");
        }

        // Proceed with booking
        Booking booking = new Booking();
        booking.setVendor(vendor);
        booking.setEvent(event);

        return bookingRepository.save(booking);
    }



    public void cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        bookingRepository.delete(booking);
    }
}
