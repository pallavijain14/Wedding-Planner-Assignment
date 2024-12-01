package org.planner.vendors;


import org.planner.booking.Booking;
import org.planner.booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vendors")
public class VendorController {

    @Autowired
    VendorService vendorService;


    @Autowired
     BookingService bookingService;


    @PostMapping
    public ResponseEntity<Vendor> registerVendor(@RequestBody Vendor vendor) {
        Vendor savedVendor = vendorService.registerVendor(vendor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVendor);
    }

    @PatchMapping("/{id}/updateAvailability")
    public ResponseEntity<Void> updateAvailability(@PathVariable Long id, @RequestParam Boolean available) {
        vendorService.updateAvailability(id, available);
        return ResponseEntity.ok().build();
    }

    @PostMapping("book-vendor")
    public ResponseEntity<Booking> bookVendor(@RequestParam Long vendorId,@RequestParam Long eventId) {
        Booking savedBooking = bookingService.bookVendor(vendorId,eventId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBooking);
    }
}
