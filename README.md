# Grupo 2 - Tema 3: DTO y separación entre API y base de datos

Proyecto Spring Boot que demuestra por qué no se deben devolver entidades JPA
directamente desde una API y cómo usar DTOs para separar la API de la capa de
persistencia.

## Miembros

- Oliver
- Jesús
- Boni
- Manu

## Requisitos

- JDK 17 o superior.
- Git.
- No hace falta instalar Maven: se usa el Maven Wrapper incluido.
- Para la demo se puede usar Bruno. No hace falta frontend.

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

Para comprobar que todo compila y pasan los tests:

```powershell
.\mvnw.cmd clean test
```

## Base de datos

Se usa H2 en memoria. Hibernate crea la tabla `usuarios` al arrancar y
`DataInitializer` inserta dos usuarios de prueba:

- Jesus / `jesus@correo.com` / `claveSuperSecreta123`
- Oliver / `oliver@correo.com` / `adminPassword999`

No hay que instalar ni subir ninguna base de datos al repositorio. Cada persona
que arranque el proyecto obtiene la misma base a partir del código.

Consola de H2:

```text
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:grupo2db
Usuario: sa
Contraseña: (vacía)
```

En la consola se puede ver la tabla `USUARIOS` y comprobar que la contraseña
está almacenada. En un proyecto real se almacenaría hasheada con BCrypt o
Argon2; aquí se usa texto plano únicamente para la demostración.

## Flujo de capas

Petición:

```text
Controller -> DTO de entrada -> Service -> Mapper -> Entity -> Repository -> H2
```

Respuesta:

```text
H2 -> Repository -> Entity -> Mapper -> DTO de salida -> Controller -> JSON
```

El controlador nunca debería devolver una entidad en los endpoints correctos.
La conversión se hace en el mapper y la lógica de negocio en el service.

## Endpoints

| Método | Ruta | Descripción | Estado |
|---|---|---|---|
| GET | `/api/usuarios/inseguro` | Ejemplo incorrecto: devuelve entidades con `password` | 200 |
| GET | `/api/usuarios/{id}/inseguro` | Ejemplo incorrecto: devuelve una entidad con `password` | 200 |
| GET | `/api/usuarios` | Lista usuarios usando `UsuarioResponseDTO`, sin `password` | 200 |
| GET | `/api/usuarios/{id}` | Devuelve un usuario sin `password` | 200 / 404 |
| POST | `/api/usuarios` | Crea un usuario a partir de `UsuarioCreateDTO` | 201 / 400 |

### Ejemplo del endpoint inseguro

```http
GET /api/usuarios/inseguro
```

```json
[
  {
    "id": 1,
    "nombre": "Jesus",
    "email": "jesus@correo.com",
    "password": "claveSuperSecreta123"
  }
]
```

### Ejemplo del endpoint correcto

```http
GET /api/usuarios
```

```json
[
  {
    "id": 1,
    "nombre": "Jesus",
    "email": "jesus@correo.com"
  }
]
```

### Crear un usuario

```http
POST /api/usuarios
Content-Type: application/json
```

```json
{
  "nombre": "Carlos Ruiz",
  "email": "carlos@example.com",
  "password": "claveSegura123"
}
```

Respuesta `201 Created`:

```json
{
  "id": 3,
  "nombre": "Carlos Ruiz",
  "email": "carlos@example.com"
}
```

La petición lleva `password` porque es un DTO de entrada. La respuesta no lo
lleva porque es un DTO de salida.

## Pruebas con Bruno

Crear una colección llamada `grupo2-tema3` y un entorno `Local` con la variable:

```text
baseUrl = http://localhost:8080
```

Peticiones recomendadas, en este orden:

1. `GET {{baseUrl}}/api/usuarios/inseguro`
   - Debe devolver `password`.
2. `GET {{baseUrl}}/api/usuarios`
   - No debe devolver `password`.
3. `GET {{baseUrl}}/api/usuarios/1`
   - Usuario concreto sin `password`.
4. `GET {{baseUrl}}/api/usuarios/999`
   - Debe devolver `404`.
5. `POST {{baseUrl}}/api/usuarios`
   - Header `Content-Type: application/json`.
   - Body con `nombre`, `email` y `password`.
   - Debe devolver `201` y no debe incluir `password`.
6. `GET {{baseUrl}}/api/usuarios`
   - Debe aparecer el usuario creado, sin `password`.
7. `POST {{baseUrl}}/api/usuarios` con datos inválidos
   - Nombre vacío, email incorrecto o contraseña corta.
   - Debe devolver `400`.

## Demo

1. Jesús enseña el problema: entidad `Usuario` y `GET /api/usuarios/inseguro`
   con la contraseña visible.
2. Oliver explica los DTOs y la diferencia entre DTO de entrada y de salida.
3. Boni explica el mapper, el service y la separación de responsabilidades.
4. Manu enseña el endpoint correcto, crea un usuario y comprueba que el JSON
   ya no contiene la contraseña.
5. Se abre H2 Console para ver la tabla `USUARIOS`.

## Conclusiones

- Devolver entidades acopla la API a la base de datos y puede exponer campos
  sensibles.
- Los DTOs permiten controlar exactamente qué datos entran y salen.
- El DTO de entrada permite validar y limitar los campos que acepta el cliente.
- El DTO de salida oculta información interna y mantiene un contrato estable.
- El mapper concentra las conversiones y evita mezclar responsabilidades.
- En producción, las contraseñas deben hashearse y nunca devolverse en una
  respuesta.
- Los DTOs se usan en APIs públicas, datos sensibles, distintas vistas de un
  mismo recurso y cuando se quiere evolucionar la persistencia sin romper a los
  clientes.
