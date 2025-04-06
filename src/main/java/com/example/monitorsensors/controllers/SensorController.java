package com.example.monitorsensors.controllers;

import com.example.monitorsensors.models.Sensor;
import com.example.monitorsensors.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/sensors")
public class SensorController {
    private final SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    // Получение списка всех датчиков
    @GetMapping
    public ResponseEntity<List<Sensor>> getAllSensors() {
        List<Sensor> sensors = sensorService.getAllSensors();
        return ResponseEntity.ok(sensors);
    }

    // Получение датчика по его ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getSensorById(@PathVariable Long id) {
        Optional<Sensor> sensor = sensorService.getSensorById(id);
        if (sensor.isPresent()) {
            return ResponseEntity.ok(sensor.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Создание нового датчика
    @PostMapping
    public ResponseEntity<?> createSensor(@RequestBody Sensor sensor) {
        try {
            Sensor savedSensor = sensorService.saveSensor(sensor);
            return ResponseEntity.ok(savedSensor);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // Обновление существующего датчика
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSensor(@PathVariable Long id, @RequestBody Sensor sensorDetails) {
        Optional<Sensor> sensorOptional = sensorService.getSensorById(id);
        if (!sensorOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Sensor sensor = sensorOptional.get();

        sensor.setName(sensorDetails.getName());
        sensor.setModel(sensorDetails.getModel());
        sensor.setRangeFrom(sensorDetails.getRangeFrom());
        sensor.setRangeTo(sensorDetails.getRangeTo());
        sensor.setType(sensorDetails.getType());
        sensor.setUnit(sensorDetails.getUnit());
        sensor.setLocation(sensorDetails.getLocation());
        sensor.setDescription(sensorDetails.getDescription());

        try {
            Sensor updatedSensor = sensorService.saveSensor(sensor);
            return ResponseEntity.ok(updatedSensor);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // Удаление датчика по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSensor(@PathVariable Long id) {
        Optional<Sensor> sensorOptional = sensorService.getSensorById(id);
        if (!sensorOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        sensorService.deleteSensor(id);
        return ResponseEntity.ok().build();
    }

    // Поиск датчиков по имени или модели через запрос-параметры
    @GetMapping("/search")
    public ResponseEntity<List<Sensor>> searchSensors(@RequestParam(required = false) String name,
                                                      @RequestParam(required = false) String model) {
        List<Sensor> sensors;
        if (name != null && !name.isEmpty()) {
            sensors = sensorService.searchByName(name);
        } else if (model != null && !model.isEmpty()) {
            sensors = sensorService.searchByModel(model);
        } else {
            sensors = sensorService.getAllSensors();
        }
        return ResponseEntity.ok(sensors);
    }
}
