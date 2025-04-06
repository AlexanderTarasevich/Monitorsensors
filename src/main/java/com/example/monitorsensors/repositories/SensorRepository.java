package com.example.monitorsensors.repositories;

import com.example.monitorsensors.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {

    List<Sensor> findByNameContainingIgnoreCase(String name);

    List<Sensor> findByModelContainingIgnoreCase(String model);
}
