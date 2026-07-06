# enterprise-solutions

## 1. Descripcion

`enterprise-solutions` es un monorepo backend orientado a portafolio con enfoque enterprise. Su objetivo es reflejar una base realista para un sistema que podria crecer durante varios años, con estandares de arquitectura, mantenibilidad y escalabilidad desde el inicio.

En la fase actual el proyecto incluye:

- estructura Maven del monorepo
- modulo `employee-management-api`
- configuracion base de Spring Boot
- perfiles de entorno
- integracion con Oracle XE mediante Docker Compose
- Flyway con bootstrap tecnico y migraciones de seguridad
- OpenAPI
- Actuator
- autenticacion basica stateless con JWT
- seed inicial de roles y usuario administrador
- dominio funcional inicial de empleados

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
- `JWT_SECRET`
- `JWT_EXPIRATION_SECONDS`

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

## 8. Credenciales iniciales

Credenciales iniciales del usuario administrador:

- username: `admin`
- password: `admin123`

## 9. Como probar login

Ejemplo de request:

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d "{\"username\":\"admin\",\"password\":\"admin123\"}"
```

Respuesta esperada:

```json
{
  "accessToken": "jwt-token",
  "tokenType": "Bearer",
  "expiresIn": 3600
}
```

## 10. Como usar el token Bearer

Tomar el valor de `accessToken` y enviarlo asi:

```bash
curl http://localhost:8080/api/v1/employees \
  -H "Authorization: Bearer <access-token>"
```

Swagger tambien queda preparado para usar autenticacion Bearer.

## 11. Endpoints de employees

Todos los endpoints de `employees` requieren JWT valido.

- `POST /api/v1/employees`
- `GET /api/v1/employees/{id}`
- `GET /api/v1/employees?page=0&size=10`
- `PUT /api/v1/employees/{id}`
- `PATCH /api/v1/employees/{id}/deactivate`
- `GET /api/v1/users`
- `GET /api/v1/users/{id}`
- `POST /api/v1/users`
- `PATCH /api/v1/users/{id}/disable`
- `PATCH /api/v1/users/{id}/enable`
- `PUT /api/v1/users/{id}/roles`
- `GET /api/v1/roles`
- `GET /api/v1/roles/{id}`

### 11.1 Ejemplo de creacion

```bash
curl -X POST http://localhost:8080/api/v1/employees \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <access-token>" \
  -d "{
    \"documentType\": \"DNI\",
    \"documentNumber\": \"12345678\",
    \"firstName\": \"John\",
    \"lastName\": \"Doe\",
    \"email\": \"john.doe@example.com\",
    \"phone\": \"999888777\",
    \"jobTitle\": \"Backend Engineer\",
    \"department\": \"Technology\",
    \"hireDate\": \"2026-07-02\"
  }"
```

### 11.2 Ejemplo de listado paginado

```bash
curl "http://localhost:8080/api/v1/employees?page=0&size=10" \
  -H "Authorization: Bearer <access-token>"
```

### 11.3 Ejemplo de desactivacion

```bash
curl -X PATCH http://localhost:8080/api/v1/employees/1/deactivate \
  -H "Authorization: Bearer <access-token>"
```

## 12. Endpoints de users

Todos los endpoints de `users` requieren JWT valido.

- `GET /api/v1/users`
- `GET /api/v1/users/{id}`
- `POST /api/v1/users`
- `PATCH /api/v1/users/{id}/disable`
- `PATCH /api/v1/users/{id}/enable`
- `PUT /api/v1/users/{id}/roles`

### 12.1 Ejemplo de creacion

```bash
curl -X POST http://localhost:8080/api/v1/users \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <access-token>" \
  -d "{
    \"username\": \"jsmith\",
    \"email\": \"jsmith@example.com\",
    \"password\": \"ChangeMe123\",
    \"roles\": [\"EMPLOYEE\"]
  }"
```

### 12.2 Ejemplo de asignacion de roles

```bash
curl -X PUT http://localhost:8080/api/v1/users/2/roles \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <access-token>" \
  -d "{
    \"roles\": [\"ADMIN\", \"EMPLOYEE\"]
  }"
```

## 13. Endpoints de roles

Todos los endpoints de `roles` requieren JWT valido.

- `GET /api/v1/roles`
- `GET /api/v1/roles/{id}`

### 13.1 Ejemplo de listado

```bash
curl http://localhost:8080/api/v1/roles \
  -H "Authorization: Bearer <access-token>"
```

## 14. Ejecucion local sin Docker para la API

Si Oracle ya se encuentra ejecutandose localmente o por Docker, la API puede iniciarse desde Maven:

```bash
mvn clean package
mvn -pl services/employee-management-api spring-boot:run
```

## 15. Verificaciones esperadas

Una vez iniciado el entorno:

- la API debe iniciar correctamente
- Oracle XE debe quedar disponible
- Flyway debe ejecutar las migraciones iniciales
- OpenAPI debe estar accesible
- Actuator debe estar accesible
- el endpoint de login debe responder con JWT
- los endpoints de employees deben responder solo con token valido
- los endpoints de users deben responder solo con token valido
- los endpoints de roles deben responder solo con token valido

Rutas esperadas por defecto:

- OpenAPI UI: `http://localhost:8080/swagger-ui.html`
- Actuator Health: `http://localhost:8080/actuator/health`
- Login: `POST http://localhost:8080/api/v1/auth/login`
- Employees: `http://localhost:8080/api/v1/employees`
- Users: `http://localhost:8080/api/v1/users`
- Roles: `http://localhost:8080/api/v1/roles`

## 16. Alcance actual

Esta fase no implementa aun:

- vacaciones
- asistencia
- CRUDs genericos
- modulos funcionales fuera del alcance de autenticacion

El objetivo actual es dejar autenticacion y el primer dominio funcional real listos antes de avanzar a otros dominios del monorepo.
