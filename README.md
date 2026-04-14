# GreenFlow

GreenFlow es una plataforma backend orientada a agricultura inteligente, organizada como monorepo con arquitectura de microservicios en Java y Spring Boot.

## Microservicios

- auth-service: gestiona autenticacion y usuarios.
- sensor-service: genera lecturas de sensores (simuladas) y las publica en Kafka cada 10 segundos.
- greenhouse-core: consume eventos de Kafka, procesa lecturas y las persiste en PostgreSQL.

## Tecnologias utilizadas

- Java 17
- Spring Boot
- Microservicios
- Spring Web MVC
- Spring Data JPA
- Spring Security
- Spring Kafka
- Apache Kafka + Zookeeper
- PostgreSQL
- Maven
- Docker Compose
- Lombok

## Arquitectura de eventos

- Topic Kafka: sensor-readings
- Productor: sensor-service
- Consumidor: greenhouse-core
- Flujo: sensor random -> Kafka -> greenhouse-core -> base de datos

## Estructura del repositorio

- auth-service/
- sensor-service/
- greenhouse-core/
- docker-compose.yml

## Ejecucion rapida

1. Levantar infraestructura:

```bash
docker compose up -d
```

2. Iniciar microservicios (en este orden):

- greenhouse-core
- sensor-service
- auth-service

3. Verificar funcionamiento:

- Kafka y PostgreSQL activos en Docker.
- Logs de greenhouse-core recibiendo eventos [KAFKA].

## Objetivo del proyecto

Separar responsabilidades en servicios independientes para mejorar escalabilidad, mantenimiento y despliegue continuo.
