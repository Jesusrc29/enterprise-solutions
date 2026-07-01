# ADR-0001: Arquitectura orientada al dominio

## Estado

Aprobado

## Contexto

El monorepo `enterprise-solutions` nace con la intencion de representar un sistema backend enterprise preparado para crecer durante varios anos.

El riesgo principal de comenzar con una estructura clasica `controller/service/repository` es que el sistema termine organizado alrededor de capas tecnicas y no alrededor de capacidades funcionales. Ese enfoque suele funcionar al inicio, pero con el tiempo favorece:

- services sobredimensionados
- controllers con reglas de negocio
- modelos anemicos
- acoplamiento entre transporte, negocio y persistencia
- dificultad para incorporar nuevos desarrolladores sin explicar excepciones por todos lados

Ademas, el repositorio se concibe como la base de una plataforma con futuros microservicios, por lo que la organizacion interna debe facilitar particion funcional, testabilidad y evolucion.

## Decision

Se adopta una arquitectura orientada al dominio con las capas:

- `api`
- `application`
- `domain`
- `infrastructure`
- `shared`

Cada dominio funcional del servicio seguira esta estructura de manera uniforme.

## Motivos

- mejora la separacion de responsabilidades
- hace mas visibles los limites funcionales
- reduce el riesgo de que la persistencia dicte el diseno completo
- mejora la mantenibilidad a medida que crece el numero de casos de uso
- facilita la incorporacion posterior de nuevos microservicios
- mejora testabilidad de reglas y casos de uso
- resulta mas adecuada para escenarios de modernizacion y evolucion gradual

## Consecuencias

### Positivas

- estructura mas preparada para el largo plazo
- mejor legibilidad para nuevos desarrolladores
- menor probabilidad de mezclar concerns
- base mas coherente para estandarizar servicios futuros

### Costos

- requiere mas disciplina desde el inicio
- puede parecer mas estructurada que una plantilla simple
- obliga a definir mejor fronteras y responsabilidades

## Alternativas consideradas

### Arquitectura clasica `controller/service/repository`

Fue descartada como estructura principal porque:

- degrada mejor con crecimiento rapido
- favorece capas tecnicas planas
- hace mas probable la acumulacion de logica en services genericos

### Arquitectura excesivamente compleja desde el dia uno

Tambien fue descartada una variante con patrones adicionales no justificados todavia, porque la prioridad es una base enterprise fuerte y pragmatica, no una demostracion de sobreingenieria.

## Resultado esperado

Esta decision debe permitir que `employee-management-api` funcione como implementacion de referencia del monorepo y que los siguientes servicios reutilicen una estructura consistente, mantenible y preparada para evolucion.
