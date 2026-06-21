# Sistema de Microservicios "Gamerbox"

**Plataforma de registro, seguimiento de backlogs y comunidad de videojuegos**

---

| Campo | Valor                                    |
|---|------------------------------------------|
| **Cliente** | Comunidad Gamer / Proyecto Académico     |
| **Proyecto** | Plataforma digital de backlog "Gamerbox" |
| **Asignatura** | DSY1103 — Desarrollo FullStack 1         |
| **Institución** | DuocUC, sede Puerto Montt                |
| **Evaluación** | Evaluación Parcial 2 (EP2)               |
| **Versión documento** | 2.0 (Arquitectura Gateway & Swagger)     |
| **Fecha** | Junio 2026                               |

---

## Equipo del proyecto

| Campo | Información del Equipo                                                                   |
|---|------------------------------------------------------------------------------------------|
| N.º de Equipo | Equipo N.º: 7                                                                            |
| Integrante 1 | Nombre: Darithza Scarleht Cárdenas Vargas<br>GitHub: https://github.com/choripanconfideo |
| Integrante 2 | Nombre: Matias Fernando Wenger Meza<br>GitHub: https://github.com/solaire27              |
| Integrante 3 | Nombre: Álvaro David Morales Yanquin<br>GitHub: https://github.com/visviriDeveloper      |
| Caso escogido | Gamerbox                                                                                 |
| Repositorio GitHub | https://github.com/visviriDeveloper/DSY1103-Gamerbox-07                                  |

---

## 1. Resumen del proyecto

GameBox es una plataforma social orientada a jugadores de videojuegos, diseñada para resolver la fragmentación en el seguimiento del progreso (backlogs) a través de distintas plataformas (PlayStation, Xbox, Nintendo, PC).

Este proyecto entrega una **arquitectura de 8 microservicios orquestados por un API Gateway central**, que digitaliza y centraliza la experiencia del usuario: registro de perfiles, catálogo centralizado de juegos, publicación de reseñas, comentarios, creación de listas personalizadas, y un sistema de seguidores que alimenta un feed dinámico. La solución se construyó sobre **Spring Boot 3.3.5 + Java 17**, utiliza un motor de base de datos **PostgreSQL** mediante contenedores Docker, y expone toda su estructura mediante una **documentación interactiva unificada con Swagger**.

---

## 2. Contexto y problema de negocio

### 2.1. Problema del Usuario
Actualmente, los jugadores poseen bibliotecas de juegos divididas en múltiples tiendas digitales y consolas físicas.
| Problema | Impacto |
|---|---|
| Fragmentación del progreso | El usuario olvida qué juegos tiene pendientes de terminar o jugar. |
| Falta de centralización de críticas | Las reseñas se pierden en foros aislados sin conexión con sus amigos. |
| Dificultad para compartir gustos | No existe un "Letterboxd" optimizado y enfocado 100% en el modelo de videojuegos y backlogs. |

### 2.2. Objetivo del software
Construir una plataforma que:
1. Mantenga un catálogo inmutable y seguro de videojuegos.
2. Permita a los usuarios llevar un registro detallado de su progreso mediante listas y reseñas.
3. Fomente la interacción social mediante un sistema de seguidores y un feed consolidado.
4. Provea un único punto de entrada seguro y documentado para futuros clientes (Frontend o Móvil).

---

## 3. Alcance

### 3.1. Incluido
- **8 microservicios REST independientes** orquestados mediante un proyecto padre Maven.
- **API Gateway reactivo** como única puerta de entrada.
- **Agregación centralizada de documentación OpenAPI (Swagger UI)**.
- Persistencia de datos en **PostgreSQL** montado sobre un volumen Docker para garantizar durabilidad.
- Autenticación mediante **Spring Security** (HTTP Basic) y usuarios en memoria con división de roles (`ADMIN` / `USER`).
- Comunicación síncrona interna entre microservicios mediante `WebClient` y DNS de Docker.
- Validación declarativa de datos de entrada con Bean Validation (JSR-380).

---

## 4. Stack tecnológico

| Capa | Tecnología | Versión |
|---|---|---|
| Lenguaje | Java | 17 LTS |
| Build | Apache Maven multi-módulo | 3.9+ |
| Framework Base | Spring Boot | 3.3.5 |
| Enrutamiento | Spring Cloud Gateway | 2023.0.3 |
| Documentación API | Springdoc OpenAPI (Swagger) | 2.6.0 |
| Web REST | Spring Web / Spring WebFlux | 6.1.x |
| Seguridad | Spring Security (HTTP Basic) | 6.3.x |
| Base de Datos | PostgreSQL (vía Docker Compose) | 15-alpine |

---

## 5. Arquitectura y Enrutamiento

El ecosistema aplica un estricto patrón de aislamiento. Los microservicios de negocio **no exponen puertos al mundo exterior**; toda la comunicación pública debe pasar a través del **API Gateway**.

### 5.1. La Puerta de Entrada (API Gateway)
| Componente | Puerto Público | Responsabilidad |
|---|---|---|
| **`api-gateway`** | **`8080`** | Único punto de entrada. Enruta peticiones, maneja CORS globalmente y recolecta la documentación Swagger de todos los nodos. |

### 5.2. Microservicios Internos (Ocultos tras la red Docker)
| # | Microservicio | Puerto Interno | Path base (vía Gateway) | Responsabilidad |
|---|---|---|---|---|
| 1 | `ms-usuarios` | 8081 | `/api/v1/usuarios` | Gestión de perfiles y registro público. |
| 2 | `ms-juegos` | 8082 | `/api/v1/juegos` | Catálogo central (protegido por ADMIN). |
| 3 | `ms-resenas` | 8083 | `/api/v1/resenas` | Publicación de críticas y calificaciones. |
| 4 | `ms-listas` | 8084 | `/api/v1/listas` | Creación de colecciones (backlogs, favoritos). |
| 5 | `ms-comentarios` | 8085 | `/api/v1/comentarios` | Discusión dentro de reseñas existentes. |
| 6 | `ms-seguidores` | 8086 | `/api/v1/seguidores` | Grafo social (quién sigue a quién). |
| 7 | `ms-feed` | 8087 | `/api/v1/feed` | Motor de consolidación para líneas de tiempo. |
| 8 | `ms-login` | 8088 | `/api/v1/auth` | Registro de intentos de acceso. |

---

## 6. Comunicación entre microservicios (Tráfico Este-Oeste)

Los microservicios se comunican entre sí de forma privada usando la red interna `gamerbox-network` de Docker, empleando `WebClient` y resolviendo los nombres de los contenedores mediante DNS interno (ej. `http://ms-usuarios-app:8081`), evitando sobrecargar el Gateway.

| MS Origen | MS Destino | Propósito |
|---|---|---|
| `ms-resenas` | `ms-juegos` & `ms-usuarios` | Validar existencia de juego y usuario antes de permitir una reseña. |
| `ms-listas` | `ms-usuarios` | Validar al dueño de la lista. |
| `ms-comentarios` | `ms-resenas` & `ms-usuarios`| Validar que la reseña original y el usuario existan. |
| `ms-feed` | `ms-seguidores` & `ms-resenas` | Motor de Agregación: Obtiene a quién sigue el usuario y luego extrae las reseñas de esos seguidos. |

---

## 7. Documentación API (Swagger UI)

Gracias al patrón *Swagger UI Gateway Aggregation*, no es necesario visitar la documentación de cada microservicio por separado.

1. Levanta el proyecto con Docker.
2. Ingresa en tu navegador a: **`http://localhost:8080/swagger-ui.html`**
3. Utiliza el menú desplegable en la esquina superior derecha (Select a definition) para navegar instantáneamente por los esquemas, endpoints y DTOs de los 8 microservicios del ecosistema.

---

## 8. Seguridad y Accesos

El ecosistema divide el control de tráfico en dos bloques mediante `SecurityFilterChain`:

1. **Bloque Público:** `/api/v1/usuarios`, `/api/v1/auth/login`, endpoints `GET` generales y las rutas `/v3/api-docs/**` (para Swagger) son de libre acceso.
2. **Bloque Privado:** Operaciones transaccionales (POST, PUT, DELETE, PATCH) requieren Basic Auth.

**Credenciales integradas de prueba:**
* **Admin:** `admin` / `1234` (Privilegios totales, incluyendo alteración del catálogo).
* **User:** `user` / `1234` (Usuario estándar).

---

## 9. Despliegue Local

### 9.1. Requisitos
* Docker Desktop / Docker Engine.
* Java 17 y Apache Maven.

### 9.2. Ejecución paso a paso

Para desplegar el ecosistema completo (Base de datos PostgreSQL + API Gateway + 8 Microservicios), abre una terminal en la raíz del proyecto y ejecuta los siguientes comandos:

**En Windows (PowerShell):**
```powershell
.\mvnw clean package -DskipTests ; docker-compose up -d --build

``` 
**En Linux/Mac (Bash):**
``` 
./mvnw clean package -DskipTests && docker-compose up -d --build
