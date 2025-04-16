package com.cyber.cybersecproject.Repository;

import com.cyber.cybersecproject.Model.ActionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionResponseRepository extends JpaRepository<ActionResponse, Long> {
    List<ActionResponse> findByEventId(String eventId);
}