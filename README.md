# Grupo 2 - Tema 3: DTO y separación entre API y base de datos

Proyecto Spring Boot que muestra la diferencia entre devolver una entidad JPA
y devolver un DTO.

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

Aplicación: `http://localhost:8080`

Tests:

```powershell
.\mvnw.cmd clean test
```

## Base de datos

H2 en memoria. Hibernate crea la tabla `usuarios` y `DataInitializer` inserta
dos usuarios de prueba.

H2 Console:

```text
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:grupo2db
Usuario: sa
Contraseña: (vacía)
```

Las contraseñas están en texto plano únicamente para la demostración.

## Endpoints

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/usuarios/inseguro` | Devuelve entidades con `password` |
| GET | `/api/usuarios/{id}/inseguro` | Devuelve una entidad con `password` |
| GET | `/api/usuarios` | Devuelve DTOs sin `password` |
| GET | `/api/usuarios/{id}` | Devuelve un DTO o 404 |
| POST | `/api/usuarios` | Crea un usuario y devuelve 201 sin `password` |

Ejemplo de `POST /api/usuarios`:

```json
{
  "nombre": "Carlos Ruiz",
  "email": "carlos@example.com",
  "password": "claveSegura123"
}
```

## Pruebas con Bruno

- Colección: `grupo2-tema3`
- Entorno: `Local`
- Variable: `baseUrl = http://localhost:8080`

Peticiones:

1. `GET {{baseUrl}}/api/usuarios/inseguro`
2. `GET {{baseUrl}}/api/usuarios`
3. `POST {{baseUrl}}/api/usuarios`
4. `GET {{baseUrl}}/api/usuarios`
