package org.planner.vendors;

import org.planner.checks.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VendorService {

    @Autowired
    private VendorsRepository vendorRepository;

    public Vendor registerVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    public void updateAvailability(Long id, Boolean available) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with ID: " + id));
        vendor.setAvailable(available);
        vendorRepository.save(vendor);
    }
}
