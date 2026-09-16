# TP1 · Spring Boot, API REST y arquitectura en capas

Introducción a Spring mediante una API con controladores, servicios, DTOs,
validación, manejo de errores y documentación con Swagger/OpenAPI.

La aplicación expone dos grupos de endpoints:

1. Catálogo de productos (`/api/productos`) — de solo lectura. El
   backend consume la API pública externa [DummyJSON](https://dummyjson.com/products)
   y expone su propia versión, con su propio contrato JSON.
2. Favoritos (`/api/favoritos`) — un recurso propio, con CRUD
   completo, guardado en memoria (sin persistencia real, sin JPA).

## Cómo levantar el proyecto

Requiere Java 25. Usar siempre el wrapper, nunca un `mvn` instalado aparte:

```
# Windows
.\mvnw.cmd spring-boot:run

# macOS/Linux
./mvnw spring-boot:run
```

Cuando el log muestre `Started DemoApplication`, la app queda escuchando en
`http://localhost:8080`.

Para compilar y correr los tests: `./mvnw test` (o `.\mvnw.cmd test`).

## Documentación / Swagger UI

Con la app corriendo, entrar a:

```
http://localhost:8080/swagger-ui.html
```

Ahí se ven los dos grupos de endpoints (**Productos** y **Favoritos**),
cada uno documentado con `@Tag`/`@Operation`, y se puede probar cada
operación con "Try it out" → "Execute" sin salir del navegador.

## Endpoints disponibles

| Recurso | Método | Path | Qué hace | Código de éxito |
|---|---|---|---|---|
| Salud | GET | `/health` | Chequeo de salud básico | 200 |
| Salud | GET | `/ping` | Devuelve `pong`, sin JSON | 200 |
| Productos | GET | `/api/productos` | Lista el catálogo completo (consumido de DummyJSON, mapeado a `ProductoDTO`) | 200 |
| Productos | GET | `/api/productos/{id}` | Devuelve un producto puntual; 404 si no existe | 200 / 404 |
| Favoritos | POST | `/api/favoritos` | Crea un favorito | 201 |
| Favoritos | GET | `/api/favoritos` | Lista todos los favoritos | 200 |
| Favoritos | GET | `/api/favoritos/{id}` | Devuelve un favorito puntual; 404 si no existe | 200 / 404 |
| Favoritos | PUT | `/api/favoritos/{id}` | Actualiza un favorito existente; 404 si no existe | 200 / 404 |
| Favoritos | DELETE | `/api/favoritos/{id}` | Elimina un favorito; 404 si no existe | 204 / 404 |

Ejemplos con curl:

```
curl http://localhost:8080/api/productos
curl http://localhost:8080/api/productos/1

curl -X POST http://localhost:8080/api/favoritos \
  -H "Content-Type: application/json" \
  -d '{"productoId":1,"nombreProducto":"Essence Mascara Lash Princess","comentario":"me gustan"}'

curl http://localhost:8080/api/favoritos
```

## Manejo de errores

Toda la API responde errores en un formato uniforme (`ProblemDetail`,
RFC 7807), a través de un `@RestControllerAdvice` central:

- Recurso inexistente (producto o favorito) → `404 Not Found`.
- Datos de entrada inválidos (Bean Validation en el DTO de favoritos) →
  `400 Bad Request`, con el detalle de qué campo falló y por qué.
- Falla al consumir la API externa DummyJSON (caída o timeout) →
  `502 Bad Gateway`.

## Qué está armado

- **`config/RestClientConfig`**: bean de `RestClient` apuntado a la
  `base-url` de DummyJSON (`app.dummyjson.base-url` en
  `application.properties`).
- **`config/OpenApiConfig`**: metadata general de Swagger UI.
- **`client/dummyjson/DummyJsonClient`**: cliente propio que usa el
  `RestClient` para llamar a `/products` y `/products/{id}`, traduciendo
  errores de red/HTTP a `RecursoNoEncontradoException` /
  `ServicioExternoException`.
- **`dto/producto/ProductoDTO`** + **`service/ProductoService`** +
  **`controller/ProductoController`**: catálogo de productos, con su
  propio contrato JSON (no expone el JSON externo tal cual).
- **`model/Favorito`**: entidad de dominio (id, referencia al producto,
  nota personal, fecha en la que se agregó).
- **`repository/FavoritoRepository`** (interfaz) +
  **`repository/FavoritoRepositoryEnMemoria`** (implementación en
  memoria con una colección, sin JPA).
- **`dto/favorito/FavoritoRequestDTO`** (entrada, con Bean Validation) y
  **`dto/favorito/FavoritoDTO`** (salida).
- **`service/FavoritoService`** + **`controller/FavoritoController`**:
  CRUD completo de favoritos.
- **`exception/GlobalExceptionHandler`** (+ `RecursoNoEncontradoException`
  y `ServicioExternoException`): manejo uniforme de errores para toda
  la API.

## Evidencia

Capturas de casos de éxito y de error probados en Swagger UI para cada
recurso están en la carpeta [`EVIDENCIA/`](./EVIDENCIA).

## Dependencias

- `spring-boot-starter-webmvc` — Spring MVC + Tomcat embebido.
- `spring-boot-starter-validation` — Bean Validation (`@NotNull`, `@NotBlank`, ...).
- `springdoc-openapi-starter-webmvc-ui` — Swagger UI / OpenAPI.