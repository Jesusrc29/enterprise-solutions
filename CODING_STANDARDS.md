# Estandares de Codigo

## 1. Objetivo

Este documento define las convenciones tecnicas del repositorio `enterprise-solutions`.

Principios rectores:

- mantenibilidad
- escalabilidad
- legibilidad
- separacion de responsabilidades
- testabilidad
- facilidad de onboarding para nuevos desarrolladores

## 2. Convencion general de idioma

- todo el codigo fuente debe escribirse en ingles
- toda la documentacion tecnica debe escribirse en espanol
- los ejemplos de codigo dentro de documentacion permanecen en ingles

## 3. Convenciones de nombres

Reglas generales:

- usar nombres explicitos y orientados a intencion
- evitar abreviaturas ambiguas
- evitar nombres genericos como `data`, `value`, `helper`, `manager` si no describen responsabilidad real
- preferir nombres de negocio o de caso de uso

## 4. Convenciones para Controllers

- terminar en `Controller`
- una responsabilidad HTTP por controller
- permanecer delgados
- no incorporar logica de negocio
- no construir respuestas inconsistentes entre endpoints

Ejemplos validos:

- `EmployeeController`
- `RoleController`
- `AuthenticationController`

## 5. Convenciones para Services

- terminar en `Service`
- usar nombres orientados al caso de uso o responsabilidad
- evitar `ServiceImpl` salvo necesidad real de interfaz
- separar claramente servicios de aplicacion y servicios de dominio

Ejemplos:

- `CreateEmployeeService`
- `EmployeeQueryService`
- `TokenValidationService`

## 6. Convenciones para Repositories

- terminar en `Repository`
- usar nombres del agregado o entidad raiz que administran
- los contratos de dominio deben vivir en `domain/repository`
- las implementaciones tecnicas deben vivir en `infrastructure/persistence/repository`

## 7. Convenciones para DTOs

- requests terminan en `Request`
- responses terminan en `Response`
- no usar `DTO` como comodin indiscriminado
- cada DTO debe representar un contrato concreto

## 8. Convenciones para Entities

- terminar en `Entity`
- vivir exclusivamente en infraestructura de persistencia
- no exponerse por API
- no mezclarse con domain models

## 9. Convenciones para Mappers

- terminar en `Mapper`
- reflejar claramente la frontera de transformacion
- evitar mapeos manuales dispersos dentro de controllers o services

Ejemplos:

- `EmployeeApiMapper`
- `EmployeePersistenceMapper`

## 10. Convenciones REST

- usar nombres de recursos, no verbos, en rutas REST
- mantener consistencia entre plural y singular
- versionar API cuando la estrategia del servicio lo requiera
- no filtrar detalles internos en payloads
- no devolver entidades JPA como contrato HTTP

## 11. Convenciones de HTTP Status

- `200 OK` para lectura y actualizacion exitosa
- `201 Created` para creacion exitosa
- `204 No Content` cuando no se requiera body
- `400 Bad Request` para entrada invalida
- `401 Unauthorized` para autenticacion ausente o invalida
- `403 Forbidden` para falta de permisos
- `404 Not Found` para recurso inexistente
- `409 Conflict` para colisiones de estado
- `422 Unprocessable Entity` cuando la regla de negocio lo justifique claramente
- `500 Internal Server Error` solo para errores inesperados

## 12. Uso de Optional

Reglas:

- usar `Optional` solo como valor de retorno cuando exprese ausencia legitima
- no usar `Optional` en atributos de entidad
- no usar `Optional` como parametro de metodo
- no encadenar `Optional` de forma que degrade legibilidad

## 13. Uso de Records cuando aplique

Se recomienda usar `record` para:

- request DTOs inmutables
- response DTOs inmutables
- payloads simples de error
- modelos de transporte sin comportamiento complejo

No se recomienda usar `record` para:

- entidades JPA
- modelos con ciclo de vida mutable complejo
- estructuras que requieran semantica distinta a un carrier inmutable

## 14. Uso de Lombok

Reglas:

- usar Lombok para reducir boilerplate repetitivo
- no usar Lombok para ocultar decisiones pobres de diseno
- preferir uso controlado y legible
- evitar anotaciones innecesarias en cascada

## 15. Organizacion de paquetes

Package base del repositorio:

```java
com.jesusromero.enterprise
```

Package base del primer servicio:

```java
com.jesusromero.enterprise.employee
```

Estructura por dominio:

- `api`
- `application`
- `domain`
- `infrastructure`
- `shared`

## 16. Reglas para validaciones

- validar request DTOs en `api`
- usar Bean Validation para restricciones estructurales
- mantener reglas de negocio en `application` o `domain`
- no depender unicamente de la base de datos para validar negocio

## 17. Reglas para manejo de excepciones

- no repetir `try/catch` en controllers salvo casos excepcionales justificados
- traducir errores mediante un manejador global
- separar errores de validacion, dominio, autorizacion e infraestructura
- no exponer stack traces ni mensajes internos en la respuesta publica

## 18. Reglas para logs

- logs claros, utiles y sin datos sensibles
- `INFO` para eventos operativos importantes
- `WARN` para situaciones recuperables o inusuales
- `ERROR` para fallos no controlados
- `DEBUG` solo para diagnostico controlado
- preparar el sistema para correlation IDs o trace IDs

## 19. Convenciones SQL

- nombres de tablas en `snake_case`
- nombres de columnas en `snake_case`
- claves primarias consistentes por agregado
- claves foraneas con nombres explicitos
- scripts de migracion pequenos, trazables y revisables
- no mezclar cambios estructurales no relacionados en una sola migracion

## 20. Convenciones de commits

Se usara Conventional Commits.

Formato:

```text
type(scope): short summary
```

Tipos recomendados:

- `feat`
- `fix`
- `refactor`
- `docs`
- `test`
- `build`
- `ci`
- `chore`

Ejemplos:

```text
docs(architecture): define domain-oriented service structure
feat(employee): add employee creation use case
refactor(shared): simplify api error contract
```
