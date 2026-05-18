# Sistema de Microservicios "GameBox"

**Plataforma de registro, seguimiento de backlogs y comunidad de videojuegos**

---

| Campo | Valor |
|---|---|
| **Cliente** | Comunidad Gamer / Proyecto Académico |
| **Proyecto** | Plataforma digital de backlog "GameBox" |
| **Asignatura** | DSY1103 — Desarrollo FullStack 1 |
| **Institución** | DuocUC, sede Puerto Varas |
| **Evaluación** | Evaluación Parcial 2 (EP2) |
| **Versión documento** | 1.0 |
| **Fecha** | Mayo 2026 |

---

## Equipo del proyecto

| Campo | Información del Equipo |
|---|---|
| N.º de Equipo | Equipo N.º: 7 |
| Integrante 1 | Nombre: Darithza Scarleht Cárdenas Vargas<br>GitHub: https://github.com/choripanconfideo |
| Integrante 2 | Nombre: Matias Fernando Wenger Meza<br>GitHub: https://github.com/solaire27 |
| Integrante 3 | Nombre: Álvaro David Morales Yanquin<br>GitHub: https://github.com/visviriDeveloper |
| Caso escogido | GamerBox |
| Repositorio GitHub | [https://github.com/Matias2772/GamerBox](https://github.com/visviriDeveloper/DSY1103-Gamerbox-07/) |
---

## 1. Resumen del proyecto

GameBox es una plataforma social orientada a jugadores de videojuegos, diseñada para resolver la fragmentación en el seguimiento del progreso (backlogs) a través de distintas plataformas (PlayStation, Xbox, Nintendo, PC). 

Este proyecto entrega una **arquitectura de 8 microservicios** que digitaliza y centraliza la experiencia del usuario: registro de perfiles, catálogo centralizado de juegos, publicación de reseñas, comentarios, creación de listas personalizadas (play-lists), y un sistema de seguidores que alimenta un feed dinámico. La solución se construyó sobre **Spring Boot 3.3.5 + Java 17**, utiliza un motor de base de datos **PostgreSQL** mediante contenedores Docker, y aplica el patrón **Controller → Service → Repository** en todo el ecosistema.

---

## 2. Contexto y problema de negocio

### 2.1. Dolor del Usuario
Actualmente, los jugadores poseen bibliotecas de juegos divididas en múltiples tiendas digitales y consolas físicas. 
| Dolor | Impacto |
|---|---|
| Fragmentación del progreso | El usuario olvida qué juegos tiene pendientes de terminar o jugar. |
| Falta de centralización de críticas | Las reseñas se pierden en foros aislados sin conexión con sus amigos. |
| Dificultad para compartir gustos | No existe un "Letterboxd" optimizado y enfocado 100% en el modelo de videojuegos y backlogs. |

### 2.2. Objetivo del software
Construir una plataforma que:
1. Mantenga un catálogo inmutable y seguro de videojuegos.
2. Permita a los usuarios llevar un registro detallado de su progreso mediante listas y reseñas.
3. Fomente la interacción social mediante un sistema de seguidores y un feed consolidado.
4. Aplique en tiempo real reglas de negocio estrictas para mantener la consistencia de los datos.

---

## 3. Alcance

### 3.1. Incluido
- **8 microservicios REST independientes** orquestados mediante un proyecto padre Maven.
- Persistencia de datos en **PostgreSQL** montado sobre un volumen Docker para garantizar durabilidad.
- Autenticación mediante **Spring Security** (HTTP Basic) y usuarios en memoria con división de roles (`ADMIN` / `USER`).
- Comunicación síncrona entre microservicios mediante `WebClient` (Spring WebFlux).
- Validación declarativa de datos de entrada con Bean Validation (JSR-380).
- Manejo unificado de errores mediante `@ControllerAdvice` y formato de respuesta `ApiError`.

---

## 4. Stack tecnológico

| Capa | Tecnología | Versión |
|---|---|---|
| Lenguaje | Java | 17 LTS |
| Build | Apache Maven multi-módulo | 3.9+ |
| Framework | Spring Boot | 3.3.5 |
| Web REST | Spring Web | 6.1.x |
| Cliente HTTP | Spring WebFlux (WebClient) | 6.1.x |
| Seguridad | Spring Security (HTTP Basic) | 6.3.x |
| Validación | Bean Validation + Hibernate Validator | — |
| Base de Datos | PostgreSQL (vía Docker Compose) | 15+ |
| Pruebas | Postman | 10+ |

---

## 5. Reglas de negocio

Las reglas obligatorias del ecosistema están implementadas en la capa Service y resguardadas por Spring Security.

| Regla | Descripción | MS responsable | Mecanismo |
|---|---|---|---|
| **R1 (Catálogo Seguro)** | La inserción, modificación o eliminación de un título en el catálogo oficial de juegos está restringida exclusivamente a usuarios con rol `ADMIN`. | `ms-juegos` | Spring Security (`.hasRole("ADMIN")`) |
| **R2 (Interacción Logueada)** | Para interactuar con la plataforma (publicar reseñas, comentar o seguir usuarios), el cliente debe proveer credenciales válidas. La lectura de datos es de acceso público. | `ms-resenas`, `ms-comentarios`, etc. | Spring Security (`.anyRequest().authenticated()`) |
| **R3 (Consistencia de Reseñas)** | Una reseña no puede ser publicada si el ID del Juego o el ID del Usuario no existen. | `ms-resenas` ↔ `ms-juegos` / `ms-usuarios` | WebClient (Verificación cruzada) |
| **R4 (Feed Dinámico)** | El muro (Feed) de un usuario solo debe mostrar las reseñas recientes de las personas que actualmente sigue. | `ms-feed` ↔ `ms-seguidores` / `ms-resenas` | WebClient (Consolidación) |
| **R5 (Registro Simulado)** | El inicio de sesión genera un UUID simulado y deja un registro inmutable en base de datos para auditoría de accesos. | `ms-login` | Generación de JTI |

---

## 6. Arquitectura

### 6.1. Microservicios y puertos

El ecosistema de 8 microservicios está distribuido para garantizar el Principio de Responsabilidad Única (SRP):

| # | Microservicio | Puerto | Path base | Responsabilidad |
|---|---|---|---|---|
| 1 | `ms-usuarios` | 8081 | `/api/v1/usuarios` | Gestión de perfiles y registro público. |
| 2 | `ms-juegos` | 8082 | `/api/v1/juegos` | Catálogo central (protegido por ADMIN). |
| 3 | `ms-resenas` | 8083 | `/api/v1/resenas` | Publicación de críticas y calificaciones. |
| 4 | `ms-listas` | 8084 | `/api/v1/listas` | Creación de colecciones (backlogs, favoritos). |
| 5 | `ms-comentarios` | 8085 | `/api/v1/comentarios` | Discusión dentro de reseñas existentes. |
| 6 | `ms-seguidores` | 8086 | `/api/v1/seguidores` | Grafo social (quién sigue a quién). |
| 7 | `ms-feed` | 8087 | `/api/v1/feed` | Motor de consolidación para líneas de tiempo. |
| 8 | `ms-login` | 8088 | `/api/v1/auth` | Registro de intentos de acceso. |

### 6.2. Maven Multi-módulo y Configuración Padre
Todo el proyecto está envuelto en `gamerbox-parent`. Este `pom.xml` raíz centraliza las dependencias críticas (como `spring-boot-starter-security` y `spring-boot-starter-web`), asegurando que todos los microservicios compartan la misma versión del framework y compilen en un solo paso mediante `mvn package`.

### 6.3. Patrón CSR (Estructura de Paquetes)
Cada microservicio respeta estrictamente el flujo unidireccional:
`Controller` (Recibe y valida) → `Service` (Lógica de negocio y WebClient) → `Repository` (Persistencia PostgreSQL).

---

## 7. Comunicación entre microservicios

El servicio más complejo a nivel de integración es **`ms-feed`**, el cual actúa como consolidador.

| MS Origen | MS Destino | Para qué |
|---|---|---|
| `ms-feed` | `ms-seguidores` | Obtener la lista de IDs de los usuarios que sigue el cliente. |
| `ms-feed` | `ms-resenas` | Extraer las últimas publicaciones de la lista de IDs obtenida previamente. |
| `ms-resenas` | `ms-juegos` | Validar que el juego existe antes de permitir guardar una reseña. |

---

## 8. Seguridad y Accesos

El ecosistema divide el control de tráfico en dos bloques mediante `SecurityFilterChain`:

1. **Bloque Público:** `ms-usuarios`, `ms-login` y `ms-feed` permiten tráfico abierto para el registro inicial y el enrutamiento interno (`.permitAll()`).
2. **Bloque Privado:** Los 5 servicios transaccionales requieren Basic Auth para operaciones de escritura. 

**Credenciales del sistema:**
* **Admin:** `admin` / `1234` (Puede crear juegos y reseñas).
* **User:** `user` / `1234` (Puede crear reseñas, pero no alterar el catálogo).

---

## 9. Manejo de Errores y Excepciones

Todos los servicios implementan `@RestControllerAdvice` para capturar errores y retornar un JSON estructurado (`ApiError`):
* `400 Bad Request`: Captura validaciones DTO (`@NotNull`, Enum incorrecto).
* `401 Unauthorized`: Acceso denegado por falta de credenciales.
* `404 Not Found`: Entidad no encontrada en base de datos.
* `500 Internal Server Error`: Fallo de conexión de base de datos o caída de un microservicio remoto.

---

## 10. Despliegue Local

### 10.1. Requisitos
* Docker y Docker Compose (Para orquestar la base de datos PostgreSQL y los contenedores).
* Java 17 y Apache Maven 3.9+.

### 10.2. Ejecución paso a paso

1. **Limpiar y Empaquetar el Proyecto (Maven Build):**
   Antes de construir las imágenes de Docker, es necesario compilar el código fuente y empaquetar cada microservicio en su archivo `.jar` correspondiente. Ejecuta el siguiente comando desde la raíz del proyecto (donde se encuentra el `pom.xml` padre):
   ```bash
   mvn clean package -DskipTests
2. **Construir y Levantar el Ecosistema Completo:**
   Una vez generados los archivos `.jar` en las carpetas `target/` con Maven, puedes ordenarle a Docker que construya las imágenes de cada contenedor y levante todo el ecosistema (incluyendo PostgreSQL) en segundo plano con un solo comando. Ejecuta desde la raíz del proyecto:
   ```bash
   docker-compose up -d --build
