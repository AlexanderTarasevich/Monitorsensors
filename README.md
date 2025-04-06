# Monitor Sensors

**Monitor Sensors** — это Spring Boot приложение для мониторинга датчиков. Проект использует PostgreSQL (версия 16.3) для хранения данных и Flyway для управления миграциями базы данных. Приложение упаковано с помощью Docker и Docker Compose, что позволяет быстро развернуть окружение.

---

## Особенности

- **Spring Boot REST API** для работы с данными датчиков.
- **PostgreSQL 16.3** для надежного хранения информации.
- **Flyway** для автоматического применения миграций базы данных.
- **Docker Compose** для удобного поднятия контейнеров.

---

## Требования

- [Docker Desktop](https://www.docker.com/products/docker-desktop) (с поддержкой Docker Compose)
- Git

---

## Быстрый старт

1. **Клонируйте репозиторий:**

   ```bash
   git clone https://github.com/AlexanderTarasevich/monitorsensors.git
   cd your_repository
   ```

2. **Запустите сборку и запуск контейнеров:**

   ```bash
   docker-compose up --build
   ```

   Контейнер с PostgreSQL будет доступен на хосте по порту **15432** (проброшен с порта контейнера **5432**), а приложение — на порту **8080**.

3. **Проверьте работу приложения:**

   Откройте браузер или Postman и перейдите по адресу: [http://localhost:8080](http://localhost:8080).

---

## Подключение к базе данных через IDE

Чтобы подключиться к PostgreSQL, запущенному в контейнере, используйте следующие параметры:

- **Host:** `localhost`
- **Port:** `15432`
- **Database:** `test_db`
- **User:** `postgres`
- **Password:** `change_me`

---

## Файлы конфигурации

### Dockerfile

```dockerfile
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### docker-compose.yml

```yaml
version: '3.8'

services:
  db:
    image: postgres:16.3
    container_name: pg_db
    environment:
      POSTGRES_DB: test_db
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: change_me
    ports:
      - "15432:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data

  app:
    build: .
    container_name: monitor_sensors_app
    depends_on:
      - db
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: "jdbc:postgresql://db:5432/test_db"
      SPRING_DATASOURCE_USERNAME: "postgres"
      SPRING_DATASOURCE_PASSWORD: "change_me"
      SPRING_FLYWAY_BASELINE_ON_MIGRATE: "true"

volumes:
  pgdata:
```

### application.properties

```properties
spring.application.name=Monitor Sensors
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
spring.flyway.baseline-on-migrate=true

spring.jpa.hibernate.ddl-auto=none
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

---

## Структура проекта

```
├── .idea/                     # Файлы настроек IDE 
├── .mvn/                      # Maven Wrapper
├── src/                       # Исходные коды приложения
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
│       └── java/
├── target/                    # Сборочные артефакты 
├── Dockerfile                 # Файл сборки Docker
├── docker-compose.yml         # Определение контейнеров
└── README.md                  # Этот файл
```

---

#