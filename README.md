# enterprise-solutions

## 1. Descripcion

`enterprise-solutions` es un monorepo backend orientado a portafolio con enfoque enterprise. Su objetivo es reflejar una base realista para un sistema que podria crecer durante varios anos, con estandares de arquitectura, mantenibilidad y escalabilidad desde el inicio.

En esta primera fase se implementa unicamente el bootstrap tecnico del proyecto:

- estructura Maven del monorepo
- modulo `employee-management-api`
- configuracion base de Spring Boot
- perfiles de entorno
- integracion con Oracle XE mediante Docker Compose
- Flyway inicial
- OpenAPI
- Actuator
- estructura completa de paquetes sin logica funcional

## 2. Requisitos

Requisitos recomendados para trabajar localmente:

- JDK 21
- Maven 3.9+
- Docker Desktop
- Docker Compose

## 3. Base de datos oficial

La base de datos oficial del proyecto es Oracle Database Express Edition (Oracle XE) ejecutandose mediante Docker.

La implementacion inicial usa Oracle XE, aunque la arquitectura queda preparada para soportar otros motores en el futuro.

## 4. Estructura general

```text
enterprise-solutions/
|-- docs/
|-- infrastructure/
|-- scripts/
|-- services/
|   `-- employee-management-api/
|-- ARCHITECTURE.md
|-- CODING_STANDARDS.md
|-- CONTRIBUTING.md
|-- DECISIONS.md
|-- CHANGELOG.md
|-- PROJECT_STRUCTURE.md
`-- docker-compose.yml
```

## 5. Servicio inicial

El primer servicio del monorepo es:

- `employee-management-api`

Package base:

```java
com.jesusromero.enterprise.employee
```

## 6. Variables de entorno

El repositorio incluye un archivo `.env.example` con los valores esperados para la ejecucion local.

No se incluyen credenciales reales en el repositorio.

Variables principales:

- `APP_PORT`
- `SPRING_PROFILES_ACTIVE`
- `DB_HOST`
- `DB_PORT`
- `DB_SERVICE`
- `DB_USERNAME`
- `DB_PASSWORD`
- `ORACLE_PASSWORD`
- `APP_USER`
- `APP_USER_PASSWORD`

## 7. Ejecucion local con Docker

### 7.1 Preparar entorno

Copiar `.env.example` a `.env` y ajustar valores si es necesario.

### 7.2 Levantar infraestructura y API

```bash
docker compose up --build
```

Esto debe levantar:

- Oracle XE
- `employee-management-api`

## 8. Ejecucion local sin Docker para la API

Si Oracle ya se encuentra ejecutandose localmente o por Docker, la API puede iniciarse desde Maven:

```bash
mvn clean package
mvn -pl services/employee-management-api spring-boot:run
```

## 9. Verificaciones esperadas

Una vez iniciado el entorno:

- la API debe iniciar correctamente
- Oracle XE debe quedar disponible
- Flyway debe ejecutar la migracion inicial
- OpenAPI debe estar accesible
- Actuator debe estar accesible

Rutas esperadas por defecto:

- OpenAPI UI: `http://localhost:8080/swagger-ui.html`
- Actuator Health: `http://localhost:8080/actuator/health`

## 10. Alcance de la fase actual

Esta fase no implementa:

- entidades funcionales
- controllers funcionales
- services funcionales
- repositories funcionales
- JWT
- autenticacion
- casos de uso de negocio

Su objetivo es dejar una base tecnica limpia, compilable y verificable antes de entrar a implementacion funcional.
