package org.planner;

import org.junit.jupiter.api.Test;
import org.planner.booking.BookingService;
import org.planner.checks.ValidationException;
import org.planner.clients.Client;
import org.planner.events.Event;
import org.planner.events.EventService;
import org.planner.vendors.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class WeddingPlannerApplicationTests {

	@Autowired
	private EventService eventService;

	@Autowired
	private BookingService bookingService;

	@Test
	void contextLoads() {
	}

	@Test
	void testEventDateValidation() {
		Event eventDto = new Event();
		eventDto.setDate(LocalDate.of(2023, 1, 1)); // Past date

		Exception exception = assertThrows(ValidationException.class, () -> {
			eventService.addEvent(eventDto);
		});

		assertEquals("Event date cannot be in the past.", exception.getMessage());
	}

	@Test
	void testBudgetValidation() {
		Client client = new Client();
		client.setBudget(new BigDecimal("1000"));

		Event eventDto = new Event();
		eventDto.setCost(new BigDecimal("1100")); // Exceeds budget

		Exception exception = assertThrows(ValidationException.class, () -> {
			eventService.addEvent(eventDto);
		});

		assertEquals("Client's budget will be exceeded with this booking.", exception.getMessage());
	}


	@Test
	void testVendorAvailabilityValidation() {
		Vendor vendor = new Vendor();
		vendor.setAvailableDates(List.of(LocalDate.of(2024, 12, 11)));

		Event event = new Event();
		event.setDate(LocalDate.of(2024, 12, 12)); // Date not in availableDates

		Exception exception = assertThrows(ValidationException.class, () -> {
			bookingService.bookVendor(vendor.getId(), event.getId());
		});

		assertEquals("Vendor is not available on the selected date.", exception.getMessage());
	}

}
