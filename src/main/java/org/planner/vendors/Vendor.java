package org.planner.vendors;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.planner.booking.Booking;

import java.time.LocalDate;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String serviceType;
    private Boolean available = true;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Booking> bookings;

    @ElementCollection
    private List<LocalDate> availableDates; // List of available dates

}
