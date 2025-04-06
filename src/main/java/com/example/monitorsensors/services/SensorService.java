package com.example.monitorsensors.services;

import com.example.monitorsensors.models.Sensor;
import com.example.monitorsensors.repositories.SensorRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SensorService {
    private final SensorRepository sensorRepository;
    private final Validator validator;

    @Autowired
    public SensorService(SensorRepository sensorRepository, Validator validator) {
        this.sensorRepository = sensorRepository;
        this.validator = validator;
    }

    public Sensor saveSensor(Sensor sensor) {
        Set<ConstraintViolation<Sensor>> violations = validator.validate(sensor);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ConstraintViolation<Sensor> violation : violations) {
                errorMessage.append(violation.getPropertyPath())
                        .append(": ")
                        .append(violation.getMessage())
                        .append("; ");
            }
            throw new IllegalArgumentException("Ошибка валидации: " + errorMessage);
        }

        if (sensor.getRangeFrom() >= sensor.getRangeTo()) {
            throw new IllegalArgumentException("Значение 'rangeFrom' должно быть меньше чем 'rangeTo'");
        }

        return sensorRepository.save(sensor);
    }


    public List<Sensor> getAllSensors() {
        return sensorRepository.findAll();
    }


    public Optional<Sensor> getSensorById(Long id) {
        return sensorRepository.findById(id);
    }


    public void deleteSensor(Long id) {
        sensorRepository.deleteById(id);
    }


    public List<Sensor> searchByName(String name) {
        return sensorRepository.findByNameContainingIgnoreCase(name);
    }


    public List<Sensor> searchByModel(String model) {
        return sensorRepository.findByModelContainingIgnoreCase(model);
    }
}
