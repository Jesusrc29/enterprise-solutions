# Estructura del Proyecto

## 1. Estructura propuesta del monorepo

El repositorio se organizara como un monorepo backend enterprise con separacion clara entre servicios, infraestructura, documentacion y decisiones tecnicas.

```text
enterprise-solutions/
|-- docs/
|   |-- architecture/
|   |-- adr/
|   |-- api/
|   |-- database/
|   |-- deployment/
|   |-- diagrams/
|   `-- decisions/
|-- services/
|   `-- employee-management-api/
|-- infrastructure/
|   |-- docker/
|   `-- compose/
|-- scripts/
|-- ARCHITECTURE.md
|-- CODING_STANDARDS.md
|-- CONTRIBUTING.md
|-- DECISIONS.md
|-- CHANGELOG.md
|-- LICENSE
`-- PROJECT_STRUCTURE.md
```

### Responsabilidad de cada carpeta de alto nivel

- `docs/`: documentacion tecnica especializada y material de soporte
- `docs/architecture/`: documentacion arquitectonica complementaria
- `docs/adr/`: ADRs del proyecto
- `docs/api/`: contratos, criterios y notas de API
- `docs/database/`: documentacion de modelo relacional, migraciones y lineamientos SQL
- `docs/deployment/`: documentacion de despliegue y operacion
- `docs/diagrams/`: espacio reservado para diagramas futuros
- `docs/decisions/`: material adicional de decisiones tecnicas o comparativas
- `services/`: microservicios del monorepo
- `infrastructure/`: activos de infraestructura local
- `scripts/`: utilitarios de automatizacion y soporte operativo

## 2. Package base oficial

No se usaran packages genericos como `com.example`.

Package base institucional:

```java
com.jesusromero.enterprise
```

Package base del primer servicio:

```java
com.jesusromero.enterprise.employee
```

## 3. Estructura propuesta de `employee-management-api`

El primer servicio se organizara por dominio y luego por capa interna.

```text
services/
`-- employee-management-api/
    |-- pom.xml
    |-- README.md
    `-- src/
        |-- main/
        |   |-- java/
        |   |   `-- com/jesusromero/enterprise/employee/
        |   |       |-- config/
        |   |       |-- security/
        |   |       |-- shared/
        |   |       |-- auth/
        |   |       |-- users/
        |   |       |-- roles/
        |   |       |-- employees/
        |   |       `-- audit/
        |   `-- resources/
        |       |-- db/
        |       |   `-- migration/
        |       |-- application.yml
        |       |-- application-local.yml
        |       |-- application-dev.yml
        |       `-- application-prod.yml
        `-- test/
            `-- java/
```

## 4. Paquetes principales del primer servicio

El primer servicio debera contener como minimo estos paquetes principales:

- `auth`
- `users`
- `roles`
- `employees`
- `audit`
- `security`
- `shared`
- `config`

## 5. Estructura interna por dominio

Cada dominio funcional debera contener:

- `api`
- `application`
- `domain`
- `infrastructure`

Ejemplo:

```text
employees/
|-- api/
|   |-- controller/
|   `-- dto/
|-- application/
|   |-- mapper/
|   `-- service/
|-- domain/
|   |-- model/
|   |-- repository/
|   `-- service/
`-- infrastructure/
    |-- persistence/
    |   |-- entity/
    |   `-- repository/
    `-- client/
```

## 6. Ejemplo ampliado de estructura de carpetas

```text
services/
`-- employee-management-api/
    |-- pom.xml
    `-- src/
        |-- main/
        |   |-- java/
        |   |   `-- com/jesusromero/enterprise/employee/
        |   |       |-- config/
        |   |       |   |-- OpenApiConfig.java
        |   |       |   `-- MapperConfig.java
        |   |       |-- security/
        |   |       |   |-- JwtAuthenticationFilter.java
        |   |       |   |-- SecurityConfig.java
        |   |       |   `-- TokenProvider.java
        |   |       |-- shared/
        |   |       |   |-- exception/
        |   |       |   |-- response/
        |   |       |   `-- util/
        |   |       |-- auth/
        |   |       |   |-- api/
        |   |       |   |-- application/
        |   |       |   |-- domain/
        |   |       |   `-- infrastructure/
        |   |       |-- users/
        |   |       |   |-- api/
        |   |       |   |-- application/
        |   |       |   |-- domain/
        |   |       |   `-- infrastructure/
        |   |       |-- roles/
        |   |       |   |-- api/
        |   |       |   |-- application/
        |   |       |   |-- domain/
        |   |       |   `-- infrastructure/
        |   |       |-- employees/
        |   |       |   |-- api/
        |   |       |   |-- application/
        |   |       |   |-- domain/
        |   |       |   `-- infrastructure/
        |   |       `-- audit/
        |   |           |-- api/
        |   |           |-- application/
        |   |           |-- domain/
        |   |           `-- infrastructure/
        |   `-- resources/
        |       |-- db/migration/
        |       |-- application.yml
        |       |-- application-local.yml
        |       |-- application-dev.yml
        |       `-- application-prod.yml
        `-- test/
            `-- java/
```

## 7. Convenciones de nombres

### Controllers

- deben terminar en `Controller`
- deben exponer una responsabilidad acotada
- deben permanecer centrados en transporte HTTP

Ejemplos:

- `EmployeeController`
- `RoleController`
- `AuthenticationController`

### Services

- los servicios de aplicacion deben terminar en `Service`
- se debe evitar `ServiceImpl` salvo que una abstraccion por interfaz tenga valor real
- los domain services tambien pueden usar `Service`, pero en su capa correspondiente

Ejemplos:

- `CreateEmployeeService`
- `EmployeeQueryService`
- `RoleAssignmentService`

### Repositories

- los contratos de repositorio deben terminar en `Repository`
- la diferenciacion entre contrato e implementacion se resuelve por paquete, no por nombres ambiguos

Ejemplos:

- `EmployeeRepository`
- `RoleRepository`

### DTOs

- request models deben terminar en `Request`
- response models deben terminar en `Response`
- no usar `DTO` como nombre generico cuando el objeto representa un contrato especifico

Ejemplos:

- `CreateEmployeeRequest`
- `EmployeeResponse`
- `AssignRoleRequest`

### Mappers

- deben terminar en `Mapper`
- el nombre debe reflejar claramente la frontera que resuelven

Ejemplos:

- `EmployeeApiMapper`
- `EmployeePersistenceMapper`
- `RoleMapper`

### Entities

- deben terminar en `Entity`
- solo pueden vivir dentro de `infrastructure/persistence/entity`

Ejemplos:

- `EmployeeEntity`
- `RoleEntity`

## 8. Reglas para separar DTOs, entidades y modelos de dominio

Estos tres conceptos deben permanecer separados.

### DTOs

Los DTOs pertenecen a la capa `api` y definen contratos de transporte.

Reglas:

- nunca exponer entidades JPA en respuestas HTTP
- nunca recibir entidades JPA como request body
- los request y response models deben permanecer orientados al contrato HTTP

### Domain models

Los domain models representan significado funcional y reglas del negocio.

Reglas:

- no deben estar modelados por conveniencia del JSON
- no deben depender de concerns de controller
- no deben contaminarse con detalles de persistencia salvo una excepcion deliberada y documentada

### Entities

Las entities representan la forma de persistencia.

Reglas:

- viven solo en infraestructura
- pueden reflejar tablas, relaciones y detalles relacionales
- no deben convertirse en el modelo universal del sistema

## 9. Reglas para controllers

Los controllers deben ser delgados.

Deben:

- recibir requests HTTP
- validar request DTOs
- delegar en servicios de aplicacion
- transformar respuestas a response DTOs
- devolver status codes correctos

No deben:

- contener reglas de negocio
- acceder directamente a base de datos
- repetir traduccion manual de excepciones
- coordinar infraestructura de forma ad hoc
- implementar logica de autorizacion inline

## 10. Reglas para servicios de aplicacion

Los servicios de aplicacion orquestan casos de uso.

Pueden:

- coordinar modelos de dominio
- invocar repositorios por contrato
- delimitar transacciones
- coordinar multiples colaboradores

No deben:

- comportarse como controllers HTTP
- contener detalles de entrega web
- transformarse en contenedores genericos de logica sin cohesion

## 11. Reglas para infraestructura

La infraestructura soporta al dominio, no lo define.

Reglas:

- persistencia en `infrastructure`
- clientes externos en `infrastructure`
- cache en `infrastructure`
- las implementaciones tecnicas deben cumplir contratos definidos en capas superiores

## 12. Reglas para `shared`

El paquete `shared` debe ser pequeno y disciplinado.

Permitido:

- excepciones compartidas
- manejador global de excepciones
- payloads genericos de error
- constantes transversales
- utilitarios realmente compartidos

No permitido:

- logica de negocio escondida
- helpers de dominio mal ubicados
- acumulacion de utilitarios sin ownership

## 13. Reglas de evolucion para nuevos servicios

Todo nuevo servicio del monorepo debera:

- respetar la misma estructura orientada al dominio
- definir fronteras funcionales claras desde el inicio
- reutilizar convenciones antes de introducir nuevas variantes
- documentar desviaciones mediante ADR

Esto permitira que el monorepo crezca sin perder coherencia.
