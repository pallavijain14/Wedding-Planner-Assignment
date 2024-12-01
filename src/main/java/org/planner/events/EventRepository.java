package org.planner.events;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event,Long> {

    @Query("SELECT e FROM Event e WHERE e.date >= :now")
    List<Event> findUpcomingEvents(@Param("now") LocalDate now);

    @Query("SELECT e FROM Event e WHERE e.date < :now")
    List<Event> findCompletedEvents(@Param("now") LocalDate now);

    List<Event> findByClientId(Long clientId);
}
