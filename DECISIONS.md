# Decisiones Arquitectonicas

Este documento registra las decisiones arquitectonicas iniciales del monorepo `enterprise-solutions` en un formato resumido tipo ADR.

## ADR-001: Usar Java 21 como runtime base

### Decision

Usar Java 21 como version estandar de JDK para los servicios backend del repositorio.

### Motivo

- es una version LTS moderna
- ofrece madurez de JVM para sistemas enterprise
- habilita un baseline actual para los proximos anos
- evita comenzar desde una base tecnologica envejecida

### Consecuencia

El repositorio queda alineado con practicas modernas de Java empresarial y preparado para evolucion de largo plazo.

## ADR-002: Usar Spring Boot 3.5.x como framework principal

### Decision

Usar Spring Boot 3.5.x como framework principal de aplicacion.

### Motivo

- ecosistema maduro para APIs enterprise
- soporte nativo para seguridad, validacion, persistencia y observabilidad
- compatibilidad con Java 21
- alineacion con el ecosistema Jakarta actual

### Consecuencia

El proyecto parte de una base vigente y ampliamente reconocida en entornos corporativos.

## ADR-003: Usar Maven como herramienta de build

### Decision

Usar Maven como estandar de build y gestion de dependencias.

### Motivo

- es ampliamente adoptado en ecosistemas Java enterprise
- tiene una convencion estable y predecible
- encaja naturalmente con Spring Boot y pipelines CI
- es facil de entender para nuevos desarrolladores

### Consecuencia

El monorepo queda con una base de build convencional, legible y facil de escalar.

## ADR-004: Usar PostgreSQL como base de datos relacional principal

### Decision

Usar PostgreSQL como motor relacional principal.

### Motivo

- es robusto, maduro y probado en produccion
- ofrece excelente soporte SQL
- evita dependencia de motores propietarios
- se integra muy bien con Spring y Flyway

### Consecuencia

La plataforma adopta una base de datos enterprise, moderna y portable.

## ADR-005: Usar Flyway para migraciones

### Decision

Usar Flyway como mecanismo oficial de versionado de esquema.

### Motivo

- evita deriva manual no documentada
- vuelve repetible la evolucion de base de datos
- facilita auditoria tecnica
- integra el esquema al ciclo de vida del software

### Consecuencia

Los cambios de base de datos pasaran a ser parte formal del desarrollo y revision del sistema.

## ADR-006: Usar arquitectura orientada al dominio

### Decision

Organizar los servicios por dominio con capas `api`, `application`, `domain`, `infrastructure` y `shared`.

### Motivo

- escala mejor que una estructura plana por capas tecnicas
- mejora separacion de responsabilidades
- facilita extraccion futura de nuevos microservicios
- reduce acoplamiento entre controllers, negocio y persistencia
- representa una decision propia de un sistema preparado para evolucion

### Consecuencia

El diseno inicial exige mas disciplina, pero entrega mejor mantenibilidad y crecimiento controlado.

## ADR-007: Usar Spring Data JPA para la persistencia inicial

### Decision

Usar Spring Data JPA como estrategia inicial de persistencia.

### Motivo

- acelera la implementacion inicial
- es familiar para la mayoria de equipos Java
- ofrece una buena relacion entre productividad y mantenibilidad
- es suficiente para el dominio inicial previsto

### Consecuencia

La persistencia arranca con una solucion pragmatica, manteniendo la puerta abierta a evolucion posterior si aparecen necesidades distintas.

## ADR-008: Usar MapStruct para mapeos

### Decision

Usar MapStruct para mapear DTOs, entidades y modelos de dominio cuando exista una frontera explicita entre capas.

### Motivo

- evita boilerplate manual repetitivo
- hace visibles los limites entre modelos
- genera mapeos en tiempo de compilacion
- ayuda a preservar separacion entre API, dominio y persistencia

### Consecuencia

Los mapeos dejan de ser logica dispersa y pasan a ser una parte explicita de la arquitectura.

## ADR-009: Usar Lombok de forma controlada

### Decision

Usar Lombok solo para reducir boilerplate, no para ocultar decisiones de diseno.

### Motivo

- mejora legibilidad en clases de soporte
- reduce ruido de codigo repetitivo
- permite concentrarse en estructura y comportamiento

### Consecuencia

El codigo puede ser mas limpio, pero el uso de Lombok debe mantenerse disciplinado y predecible.

## ADR-010: Usar Spring Security 6 como baseline de seguridad

### Decision

Usar Spring Security 6 como framework base de seguridad.

### Motivo

- es el estandar moderno del ecosistema Spring
- soporta bien APIs stateless
- se integra naturalmente con JWT y evoluciones futuras
- centraliza seguridad en un marco maduro

### Consecuencia

La seguridad se resuelve de forma coherente y mantenible, evitando soluciones manuales dispersas.

## ADR-011: Usar JWT inicialmente

### Decision

Usar JWT como mecanismo inicial de autenticacion para APIs protegidas.

### Motivo

- es un punto de partida practico para una API enterprise
- permite un modelo stateless
- es compatible con arquitecturas distribuidas
- deja una ruta clara hacia integraciones futuras mas complejas

### Consecuencia

El sistema comienza con un enfoque pragmatico y evolutivo, sin bloquear una futura integracion con un proveedor de identidad.

## ADR-012: Usar Swagger/OpenAPI para documentacion de API

### Decision

Usar Swagger/OpenAPI para exponer contratos de API.

### Motivo

- mejora descubribilidad
- acelera onboarding tecnico
- hace visible el contrato desde etapas tempranas
- refleja una practica normal en APIs enterprise

### Consecuencia

La API tendra una base formal de documentacion tecnica desde su primera implementacion.

## ADR-013: Usar Docker y Docker Compose para entorno local

### Decision

Usar Docker y Docker Compose para el entorno local y la orquestacion inicial de dependencias.

### Motivo

- mejora reproducibilidad
- reduce problemas de entorno entre desarrolladores
- facilita la incorporacion de nuevas dependencias tecnicas
- acerca el entorno local a un flujo real de despliegue

### Consecuencia

El proyecto sera mas facil de levantar, compartir y mantener entre varios desarrolladores.

## ADR-014: Usar JUnit 5 y Mockito como baseline de pruebas

### Decision

Usar JUnit 5 y Mockito como stack inicial de pruebas unitarias y de servicios.

### Motivo

- son herramientas maduras y estandar
- permiten una base solida sin sobredisenar la estrategia de testing
- facilitan pruebas de reglas, servicios y componentes aislados

### Consecuencia

La calidad del codigo podra validarse desde etapas tempranas con herramientas conocidas y mantenibles.

## ADR-015: Usar manejo global de excepciones

### Decision

Traducir excepciones de forma centralizada en vez de repetir logica HTTP en cada controller.

### Motivo

- mantiene controllers delgados
- mejora consistencia del contrato de error
- reduce duplicacion
- facilita mantenimiento y evolucion

### Consecuencia

La API sera mas uniforme y facil de consumir y extender.

## ADR-016: Mantener la validacion de transporte en el borde y las reglas en dominio

### Decision

Separar validacion de request DTOs de reglas de negocio.

### Motivo

- evita mezclar concerns tecnicos y funcionales
- protege mejor la coherencia del dominio
- evita controllers sobrecargados

### Consecuencia

La validacion queda distribuida correctamente por capa, mejorando claridad y testabilidad.

## ADR-017: No copiar patrones legacy antiguos

### Decision

No replicar patrones estructurales antiguos solo porque existieron en sistemas previos.

### Motivo

- los sistemas legacy responden a restricciones historicas
- copiar estructura antigua perpetua deuda tecnica
- modernizar implica conservar aprendizajes utiles, no heredar acoplamientos innecesarios

### Consecuencia

El monorepo se beneficia de experiencia real sin arrastrar decisiones obsoletas.

## ADR-018: Preferir modernizacion evolutiva sobre sobreingenieria temprana

### Decision

Disenar para crecimiento, pero sin introducir complejidad innecesaria en la primera fase.

### Motivo

- una arquitectura enterprise no debe ser inflada artificialmente
- la complejidad solo debe entrar cuando agrega valor real
- la prioridad es una base fuerte, no una demostracion excesiva de patrones

### Consecuencia

El proyecto conserva una identidad profesional y escalable sin perder pragmatismo.

## ADR-019: Establecer ingles para codigo y espanol para documentacion

### Decision

Usar ingles en todo el codigo fuente y espanol en toda la documentacion tecnica.

### Motivo

- el ingles es el estandar natural del codigo profesional
- el espanol mejora comprension y transferencia de conocimiento para el publico objetivo del repositorio
- la separacion reduce ambiguedad sobre donde aplicar cada idioma

### Consecuencia

El repositorio tendra una identidad coherente, profesional y accesible para desarrolladores hispanohablantes.

## ADR-020: Eliminar packages genericos tipo `com.example`

### Decision

No usar packages base genericos y adoptar una identidad de namespace propia.

### Motivo

- evita apariencia de plantilla generica
- fortalece identidad del proyecto
- mejora consistencia desde la raiz del codigo

### Consecuencia

El package base oficial del repositorio sera:

```java
com.jesusromero.enterprise
```

Y para el primer servicio:

```java
com.jesusromero.enterprise.employee
```
