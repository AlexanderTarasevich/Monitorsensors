package com.example.monitorsensors.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название не может быть пустым")
    @Size(min = 3, max = 30, message = "Название должно быть от 3 до 30 символов")
    private String name;

    @NotBlank(message = "Модель не может быть пустой")
    @Size(max = 15, message = "Модель должна быть не более 15 символов")
    private String model;

    @Positive(message = "Начальный диапазон должен быть положительным")
    private Integer rangeFrom;

    @Positive(message = "Конечный диапазон должен быть положительным")
    private Integer rangeTo;

    @NotNull(message = "Тип датчика обязателен")
    @Enumerated(EnumType.STRING)
    private SensorType type;

    @NotNull(message = "Единица измерения обязательна")
    @Enumerated(EnumType.STRING)
    private MeasurementUnit unit;

    @Size(max = 40, message = "Местоположение должно быть не более 40 символов")
    private String location;

    @Size(max = 200, message = "Описание должно быть не более 200 символов")
    private String description;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getRangeFrom() {
        return rangeFrom;
    }

    public void setRangeFrom(Integer rangeFrom) {
        this.rangeFrom = rangeFrom;
    }

    public Integer getRangeTo() {
        return rangeTo;
    }

    public void setRangeTo(Integer rangeTo) {
        this.rangeTo = rangeTo;
    }

    public SensorType getType() {
        return type;
    }

    public void setType(SensorType type) {
        this.type = type;
    }

    public MeasurementUnit getUnit() {
        return unit;
    }

    public void setUnit(MeasurementUnit unit) {
        this.unit = unit;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
