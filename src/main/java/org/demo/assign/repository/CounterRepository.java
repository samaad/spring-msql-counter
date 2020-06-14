package org.demo.assign.repository;

import org.demo.assign.model.CounterTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CounterRepository extends JpaRepository<CounterTracker,Integer> {
}
