package org.planner.clients;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByWeddingDate(LocalDate weddingDate);

    List<Client>findByBudgetBetween(Double minBudget, Double maxBudget);
}
