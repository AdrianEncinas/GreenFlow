# GreenFlow

GreenFlow es una plataforma backend orientada a agricultura inteligente, organizada como monorepo con arquitectura de microservicios en Java y Spring Boot.

## Microservicios

| Servicio | Puerto | Descripcion |
|---|---|---|
| `auth-service` | 8080 | Gestion de usuarios y autenticacion |
| `sensor-service` | 8082 | Generacion y publicacion de lecturas de sensores |
| `greenhouse-core` | 8081 | Consumo de eventos Kafka y persistencia de lecturas |

---

## Tecnologias

- Java 17
- Spring Boot 4.x
- Spring Web MVC
- Spring Data JPA
- Spring Security (BCrypt, sesiones sin estado)
- Spring Kafka
- Apache Kafka + Zookeeper
- PostgreSQL
- H2 (base de datos en memoria para tests)
- Springdoc OpenAPI 3 (Swagger UI)
- Maven
- Docker Compose
- Lombok

---

## Arquitectura

Todos los servicios siguen **arquitectura hexagonal (Ports & Adapters)**:

```
domain/           → Modelos y excepciones de negocio
application/
  port/in/        → Casos de uso (interfaces de entrada)
  port/out/       → Puertos de salida (persistencia, seguridad)
  service/        → Logica de aplicacion
infrastructure/
  adapter/in/     → Adaptadores de entrada (REST, Kafka)
  adapter/out/    → Adaptadores de salida (JPA, BCrypt)
```

### Flujo de eventos

```
SensorScheduler (cada 10s)
  → SensorProducer
  → Kafka (topic: sensor-readings)
  → SensorReadingConsumer (greenhouse-core)
  → SensorReadingApplicationService
  → PostgreSQL (tabla: sensor_readings)
  → SensorReadingController (REST)
```

---

## API - auth-service (`/api/v1/users`)

| Metodo | Endpoint | Descripcion |
|---|---|---|
| GET | `/list` | Listar todos los usuarios |
| GET | `/get/{id}` | Obtener usuario por ID |
| POST | `/create` | Crear nuevo usuario |
| PUT | `/update/{id}` | Actualizar usuario existente |
| DELETE | `/delete/{id}` | Eliminar usuario |

Swagger UI disponible en: `http://localhost:8080/swagger-ui.html`

## API - greenhouse-core (`/api/v1/sensor-readings`)

| Metodo | Endpoint | Descripcion |
|---|---|---|
| GET | `/list` | Listar todas las lecturas |
| GET | `/get/{id}` | Obtener lectura por ID |
| GET | `/by-sensor/{sensorId}` | Lecturas de un sensor especifico |

---

## Estructura del repositorio

```
GreenFlow/
├── auth-service/       → Usuarios y autenticacion
├── sensor-service/     → Productor Kafka de sensores
├── greenhouse-core/    → Consumidor Kafka + API de lecturas
└── docker-compose.yml  → Kafka, Zookeeper y PostgreSQL
```

---

## Ejecucion rapida

### 1. Levantar infraestructura

```bash
docker compose up -d
```

### 2. Iniciar microservicios (en este orden)

```bash
# greenhouse-core
cd greenhouse-core && ./mvnw spring-boot:run

# sensor-service
cd sensor-service && ./mvnw spring-boot:run

# auth-service
cd auth-service && ./mvnw spring-boot:run
```

### 3. Verificar funcionamiento

- Kafka y PostgreSQL activos en Docker.
- Logs de `greenhouse-core` recibiendo eventos con prefijo `[KAFKA]`.
- Swagger UI de auth-service en `http://localhost:8080/swagger-ui.html`.

---

## Tests

Los tests unitarios del `auth-service` usan Mockito y no requieren base de datos ni contexto Spring.

```bash
cd auth-service && ./mvnw test
```

Cobertura de tests:

| Clase | Tests |
|---|---|
| `UserApplicationService` | crear, listar, obtener por ID, actualizar (con/sin password), eliminar |
| `UserController` | crear, listar, obtener por ID, actualizar, eliminar |
| `V1Application` | carga del contexto Spring |

---

## Objetivo del proyecto

Separar responsabilidades en servicios independientes para mejorar escalabilidad, mantenimiento y despliegue continuo.

