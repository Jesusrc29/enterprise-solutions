# Arquitectura

## 1. Vision general

`enterprise-solutions` es un monorepo backend de nivel profesional orientado a portafolio, disenado para reflejar decisiones arquitectonicas reales de un entorno enterprise y no un proyecto academico ni una plantilla generica.

El repositorio busca demostrar experiencia en:

- arquitectura de microservicios orientada al dominio
- modernizacion y reemplazo de sistemas legacy
- seguridad de APIs
- mantenibilidad a largo plazo
- escalabilidad tecnica y organizacional
- observabilidad y operacion
- despliegue reproducible
- crecimiento controlado del ecosistema

La intencion del proyecto es parecer el inicio de una plataforma que podria evolucionar durante varios anos, incorporando nuevos equipos, nuevos microservicios y nuevas capacidades sin degradar su coherencia tecnica.

## 2. Vision del monorepo

El monorepo sera el punto unico de verdad para los servicios backend, la documentacion tecnica, las decisiones arquitectonicas y los activos de infraestructura local.

Objetivos principales:

- unificar convenciones tecnicas entre servicios
- reducir friccion de arranque para nuevos desarrolladores
- centralizar la documentacion arquitectonica
- facilitar la incorporacion de nuevos microservicios
- mantener consistencia en seguridad, observabilidad y calidad
- evitar deriva estructural entre servicios

El monorepo comenzara con un primer microservicio llamado `employee-management-api`, que funcionara como implementacion de referencia para el resto del ecosistema.

## 3. Convencion oficial de idioma

La convencion del repositorio sera definitiva y transversal:

- el codigo fuente siempre estara en ingles
- la documentacion tecnica siempre estara en espanol
- los fragmentos de codigo dentro de la documentacion permaneceran en ingles, porque representan codigo fuente

### Alcance de la convencion para codigo fuente

Siempre en ingles:

- paquetes
- clases
- interfaces
- metodos
- variables
- constantes
- enums
- DTOs
- entidades
- controladores
- servicios
- repositorios
- mappers
- endpoints REST
- respuestas JSON
- mensajes de commit

### Alcance de la convencion para documentacion

Siempre en espanol:

- `README.md`
- `ARCHITECTURE.md`
- `DECISIONS.md`
- `PROJECT_STRUCTURE.md`
- `CODING_STANDARDS.md`
- `CONTRIBUTING.md`
- `CHANGELOG.md`
- ADRs
- documentacion dentro de `docs/`
- comentarios explicativos de arquitectura

Esta convencion busca que el proyecto sea natural para desarrolladores hispanohablantes sin sacrificar las practicas internacionales de ingenieria de software.

## 4. Objetivo del primer microservicio: `employee-management-api`

El primer microservicio existe para establecer la base arquitectonica del monorepo.

Su objetivo no es demostrar un CRUD basico, sino servir como referencia de como se construye un servicio enterprise desde el inicio:

- limites de dominio claros
- contratos HTTP consistentes
- capas bien definidas
- seguridad desacoplada
- persistencia aislada
- validacion predecible
- manejo global de errores
- preparacion para observabilidad y crecimiento

Conceptualmente, este servicio representa el tipo de API que una organizacion construiria al modernizar una plataforma interna y reemplazar componentes legacy por una base Java moderna con mejor mantenibilidad y mejor capacidad de evolucion.

## 5. Estilo arquitectonico seleccionado

La arquitectura elegida es orientada al dominio, con una organizacion interna por capas:

- `api`
- `application`
- `domain`
- `infrastructure`
- `shared`

Esta estructura se aplicara por dominio funcional y no solo a nivel global del servicio.

## 6. Responsabilidad de las capas

### `api`

Contiene los elementos expuestos hacia HTTP:

- controllers
- request DTOs
- response DTOs
- validacion de entrada
- adaptacion entre el contrato HTTP y los casos de uso de aplicacion

### `application`

Contiene la orquestacion de casos de uso:

- servicios de aplicacion
- coordinacion de flujo
- mappers entre contratos externos y modelos internos
- delimitacion de transacciones cuando corresponda

Esta capa coordina, pero no debe contaminarse con detalles de transporte ni de persistencia.

### `domain`

Contiene las reglas y conceptos del negocio:

- domain models
- repository contracts
- domain services
- value objects
- reglas de negocio
- excepciones de dominio

Esta capa debe permanecer lo mas independiente posible de Spring MVC, JPA y detalles tecnicos de infraestructura.

### `infrastructure`

Contiene implementaciones tecnicas:

- JPA entities
- repositorios Spring Data
- clientes externos
- integraciones de cache
- adaptadores de base de datos
- integraciones futuras con mensajeria o almacenamiento externo

### `shared`

Contiene componentes transversales no pertenecientes a un dominio especifico:

- modelo comun de errores
- manejo global de excepciones
- soporte comun de respuestas
- primitives de seguridad
- soporte transversal para logging
- utilitarios cuidadosamente acotados

## 7. Justificacion del enfoque orientado al dominio

La arquitectura clasica `controller/service/repository` funciona para comenzar rapido, pero suele degradarse cuando el sistema crece en dominios, integraciones, reglas y equipo.

Problemas frecuentes del enfoque clasico:

- servicios demasiado grandes
- controladores con logica de negocio
- diseno guiado por persistencia
- fronteras de dominio difusas
- fuerte acoplamiento entre HTTP, negocio y datos

Se eligio una arquitectura orientada al dominio porque ofrece mejores propiedades para un sistema que debe crecer durante varios anos.

### Beneficios del enfoque elegido

- mayor claridad de propiedad por capacidad funcional
- mejor separacion de responsabilidades
- mas facilidad para extraer nuevos microservicios
- mejor testabilidad de reglas y casos de uso
- menor acoplamiento entre contratos, negocio y persistencia
- mejor soporte para escenarios de migracion y reemplazo gradual de componentes legacy

## 8. Stack tecnico propuesto

Stack objetivo para `employee-management-api`:

- Java 21
- Spring Boot 3.5.x
- Maven
- Oracle Database Express Edition (Oracle XE)
- Spring Data JPA
- Flyway
- Spring Security 6
- JWT
- Swagger/OpenAPI
- MapStruct
- Lombok
- Docker
- Docker Compose
- JUnit 5
- Mockito

## 9. Lineamientos de seguridad

La seguridad sera un eje de arquitectura, no una capa agregada al final.

Principios:

- denegar por defecto
- autenticar antes de autorizar
- centralizar configuracion y filtros de seguridad
- no mezclar autorizacion con logica de negocio dentro de controllers
- proteger endpoints operativos
- no exponer secretos ni configuracion sensible en codigo
- externalizar configuracion sensible por entorno

Lineamientos iniciales:

- uso de `Spring Security 6` como estandar
- API stateless protegida con JWT bearer token
- componentes de seguridad separados en paquetes dedicados
- autorizacion basada en roles y permisos cuando aplique
- logging de eventos relevantes de seguridad sin filtrar informacion sensible

JWT sera el mecanismo inicial, pero el diseno debe permitir evolucionar hacia integracion posterior con un identity provider o un enfoque mas completo basado en OAuth2.

## 10. Lineamientos de logging

El logging debe servir para operar, diagnosticar y auditar sin volverse ruido ni filtrar informacion sensible.

Reglas base:

- `INFO` para ciclo de vida del servicio y eventos operativos relevantes
- `WARN` para situaciones recuperables o patrones anormales
- `ERROR` para fallos no controlados y problemas de infraestructura
- `DEBUG` solo para desarrollo o diagnostico puntual
- nunca registrar passwords, tokens, datos personales o payloads sensibles en texto plano
- preparar el servicio para usar correlation IDs o trace IDs desde etapas tempranas

El objetivo es dejar la base lista para observabilidad distribuida desde el inicio.

## 11. Lineamientos de manejo global de excepciones

El manejo de excepciones debe ser centralizado, consistente y predecible.

Reglas:

- los controllers no deben repetir bloques `try/catch` para comportamiento HTTP estandar
- las capas `application` y `domain` pueden emitir excepciones significativas
- un manejador global debe traducir excepciones a respuestas HTTP consistentes
- el contrato de error debe ser estable y documentado

Categorias minimas de error:

- errores de validacion
- errores de autenticacion
- errores de autorizacion
- recurso no encontrado
- violaciones de regla de negocio
- fallos de integracion o dependencia externa
- errores internos inesperados

## 12. Lineamientos de respuestas HTTP

Las respuestas HTTP deben ser claras y consistentes entre servicios.

Principios:

- usar codigos HTTP estandares correctamente
- mantener estructuras de respuesta predecibles
- separar payload de exito y payload de error
- no filtrar detalles internos de excepcion
- evitar respuestas ad hoc por endpoint

Guia base de codigos:

- `200 OK` para consultas y actualizaciones exitosas
- `201 Created` para creacion exitosa
- `204 No Content` cuando la operacion sea exitosa y no requiera body
- `400 Bad Request` para entrada invalida o mal formada
- `401 Unauthorized` para autenticacion ausente o invalida
- `403 Forbidden` para acceso autenticado pero no autorizado
- `404 Not Found` para recursos inexistentes
- `409 Conflict` para colisiones de estado
- `422 Unprocessable Entity` cuando la semantica del caso lo justifique
- `500 Internal Server Error` solo para errores inesperados

## 13. Lineamientos de validacion

La validacion debe fallar temprano y en la capa correcta.

Reglas:

- validar request DTOs en el borde de la API
- usar Bean Validation para restricciones estructurales
- mantener reglas de negocio en `application` o `domain`
- no depender exclusivamente de restricciones de base de datos para validar entradas

Separacion esperada:

- validacion de transporte en `api`
- validacion de reglas del negocio en `application` o `domain`
- restricciones de persistencia como ultima barrera, no como mecanismo principal de negocio

## 14. Lineamientos de persistencia y migraciones

La evolucion de base de datos debe ser versionada, repetible y revisable.

Reglas:

- todos los cambios de esquema deben pasar por Flyway
- no debe existir deriva manual no documentada
- las migraciones deben ser deterministas
- las entidades no deben conducir cambios de esquema de forma implicita en flujos productivos

Se eligio `Spring Data JPA` por pragmatismo y productividad, pero la arquitectura aisla persistencia dentro de `infrastructure`, permitiendo cambiar estrategia de acceso a datos en el futuro si el contexto lo exige.

La implementacion inicial utilizara Oracle XE como motor oficial del proyecto. Aun asi, la configuracion y la organizacion de la capa de persistencia deben permanecer preparadas para soportar otros motores en fases futuras, especialmente PostgreSQL y SQL Server.

## 15. Direccion de performance y cache

Aunque el primer servicio no debe sobredisenarse, la arquitectura quedara preparada para incorporar patrones de performance reales, por ejemplo:

- cache con Redis para consultas repetidas
- cache de corta duracion para lookups frecuentes
- optimizacion de lectura para endpoints de alto trafico
- estrategias especializadas cuando aparezcan consultas intensivas o patrones geoespaciales

La decision es pragmatica: no implementar cache desde el dia uno sin necesidad real, pero si dejar una estructura compatible con ese crecimiento.

## 16. Lineamientos para futuros microservicios

Todo nuevo microservicio agregado a `enterprise-solutions` debera seguir esta base arquitectonica salvo que exista una decision formal que justifique una desviacion.

Expectativas minimas:

- misma organizacion orientada al dominio
- misma disciplina de seguridad
- mismo enfoque de manejo global de excepciones
- mismas convenciones de respuestas HTTP
- mismo control de migraciones con Flyway
- mismo baseline de pruebas con JUnit 5 y Mockito
- mismo enfoque de contenedorizacion para entorno local

Capacidades futuras posibles:

- Redis
- mensajeria
- tareas programadas
- almacenamiento de archivos
- integraciones externas
- componentes reactivos
- motores de busqueda

Estas evoluciones deben introducirse por decision arquitectonica explicita, no por deriva accidental.

## 17. Package base oficial

No se utilizara nunca un package base generico como `com.example`.

Package base institucional del repositorio:

```java
com.jesusromero.enterprise
```

Package base del primer servicio:

```java
com.jesusromero.enterprise.employee
```

Esta decision mejora la identidad del proyecto, evita apariencia de plantilla generica y refuerza consistencia desde la raiz del codigo fuente.

## 18. No objetivos de la fase actual

Esta fase no implementa:

- proyecto Spring Boot
- entidades
- controllers
- services
- repositorios
- seguridad funcional
- Dockerfiles
- manifests de despliegue

El objetivo actual es dejar la arquitectura documentada, alineada y lista para comenzar la implementacion con una base enterprise coherente.
