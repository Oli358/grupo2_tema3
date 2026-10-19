# Grupo 2 - Tema 3: DTO y separación entre API y base de datos

Proyecto Spring Boot para demostrar por qué no se deben devolver entidades JPA
directamente desde una API y cómo usar DTOs para separar la API de la capa de
persistencia.

## Miembros

- Oliver
- Jesús
- Boni
- Manu

## Requisitos

- JDK 17 o superior
- Git
- No hace falta instalar Maven: se usa el Maven Wrapper incluido.

## Arranque

En Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

En macOS/Linux:

```bash
./mvnw spring-boot:run
```

La aplicación se levanta en `http://localhost:8080`.

## Base de datos

Se usa H2 en memoria. Hibernate crea las tablas al arrancar y los datos de
prueba se cargan desde `src/main/resources/data.sql`.

Consola de H2:

```text
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:grupo2db
Usuario: sa
Contraseña: (vacía)
```

No se sube ninguna base de datos al repositorio: cada persona que arranque el
proyecto obtiene la misma base a partir del código y de `data.sql`.

## Endpoints

Esta sección la completará Manu con las peticiones de Bruno y las capturas de
pantalla.

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/usuarios/inseguro` | Ejemplo incorrecto: devuelve entidades con la contraseña |
| GET | `/api/usuarios` | Ejemplo correcto: devuelve `UsuarioResponseDTO` sin contraseña |
| POST | `/api/usuarios` | Crea un usuario a partir de `UsuarioCreateDTO` |

## Demostración

Las peticiones se probarán con Bruno. La colección se añadirá en
`bruno/grupo2-tema3/` y en el README se incluirán ejemplos de uso.
