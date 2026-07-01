# Guia de Contribucion

## 1. Objetivo

Aunque el repositorio pueda iniciar con un solo desarrollador, desde el principio debe estar preparado para colaboracion futura bajo estandares enterprise.

## 2. Flujo de ramas

Estrategia propuesta:

- `main` como rama estable
- ramas de trabajo cortas a partir de `main`
- nomenclatura recomendada:
  - `feature/...`
  - `fix/...`
  - `refactor/...`
  - `docs/...`
  - `chore/...`

Reglas:

- no trabajar directamente en `main`
- mantener ramas pequenas y enfocadas
- evitar ramas gigantes de larga duracion salvo necesidad excepcional

## 3. Convencion de commits

Se usara Conventional Commits.

Formato:

```text
type(scope): short summary
```

Ejemplos:

```text
feat(employee): add employee query use case
fix(security): handle invalid token response
docs(structure): update monorepo package conventions
```

## 4. Pull Requests

Todo cambio relevante debera entrar mediante Pull Request.

Cada Pull Request debe:

- tener un objetivo claro
- ser pequeno o razonablemente acotado
- describir contexto y alcance
- incluir impacto tecnico relevante
- evitar mezclar cambios no relacionados

## 5. Revision de codigo

La revision debe priorizar:

- correccion funcional
- claridad de diseno
- coherencia con la arquitectura
- mantenibilidad
- seguridad
- facilidad de prueba

Se debe revisar especialmente:

- ubicacion correcta por capa
- ausencia de logica de negocio en controllers
- separacion entre DTOs, domain models y entities
- consistencia de manejo de errores
- calidad de nombres y legibilidad

## 6. Estandares minimos

Ningun cambio debe fusionarse si:

- rompe las convenciones del repositorio
- introduce logica en una capa incorrecta
- mezcla concerns de transporte, negocio y persistencia
- incorpora nombres ambiguos o genericos
- omite actualizacion de documentacion relevante

## 7. Calidad de codigo

Todo cambio debe buscar:

- cohesión alta
- bajo acoplamiento
- codigo legible
- buena separacion de responsabilidades
- testabilidad
- facilidad de evolucion

## 8. Regla general

Cuando existan varias soluciones validas, debe preferirse la alternativa que normalmente elegiria un equipo de arquitectura de una empresa mediana o grande:

- clara
- mantenible
- consistente
- facil de revisar
- facil de extender en el tiempo
