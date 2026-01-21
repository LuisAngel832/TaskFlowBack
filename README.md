<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.5.9-brightgreen?style=for-the-badge&logo=spring-boot" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk" alt="Java"/>
  <img src="https://img.shields.io/badge/PostgreSQL-Database-blue?style=for-the-badge&logo=postgresql" alt="PostgreSQL"/>
  <img src="https://img.shields.io/badge/JWT-Authentication-red?style=for-the-badge&logo=json-web-tokens" alt="JWT"/>
</p>

<h1 align="center">🚀 TaskFlow API</h1>

<p align="center">
  <strong>Una API RESTful moderna para gestión de proyectos y tareas colaborativas</strong>
</p>

<p align="center">
  <a href="#-características">Características</a> •
  <a href="#-tecnologías">Tecnologías</a> •
  <a href="#-inicio-rápido">Inicio Rápido</a> •
  <a href="#-endpoints">Endpoints</a> •
  <a href="#-modelos">Modelos</a> •
  <a href="#-contribuir">Contribuir</a>
</p>

---

## 📋 Descripción

**TaskFlow** es una API REST robusta diseñada para la gestión eficiente de proyectos y tareas en equipos de trabajo. Permite a los usuarios crear proyectos, asignar tareas, gestionar miembros del equipo y realizar seguimiento del progreso de las actividades.

## ✨ Características

- 🔐 **Autenticación segura** con JWT (JSON Web Tokens)
- 👥 **Gestión de usuarios** con roles y permisos
- 📁 **Gestión de proyectos** con transferencia de propiedad
- ✅ **Gestión de tareas** con estados y prioridades
- 👨‍👩‍👧‍👦 **Colaboración en equipo** - agregar/remover miembros
- 📊 **Auditoría automática** de creación y modificación
- 📖 **Documentación OpenAPI/Swagger** integrada
- ✔️ **Validación de datos** robusta

## 🛠 Tecnologías

| Tecnología | Versión | Descripción |
|------------|---------|-------------|
| **Java** | 21 | Lenguaje de programación |
| **Spring Boot** | 3.5.9 | Framework principal |
| **Spring Security** | 6.x | Seguridad y autenticación |
| **Spring Data JPA** | 3.x | Persistencia de datos |
| **PostgreSQL** | Latest | Base de datos relacional |
| **JWT (jjwt)** | 0.12.5 | Tokens de autenticación |
| **Lombok** | Latest | Reducción de boilerplate |
| **SpringDoc OpenAPI** | Latest | Documentación de API |
| **Maven** | 3.x | Gestión de dependencias |

## 🚀 Inicio Rápido

### Prerrequisitos

- ☕ Java 21 o superior
- 🐘 PostgreSQL 14+
- 📦 Maven 3.8+

### Instalación

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/tu-usuario/taskflow.git
   cd taskflow
   ```

2. **Configurar la base de datos**
   
   Crea una base de datos PostgreSQL:
   ```sql
   CREATE DATABASE taskflow_db;
   ```

3. **Configurar variables de entorno**
   
   Copia el archivo de ejemplo y configura tus credenciales:
   ```bash
   cp application.properties.example src/main/resources/application.properties
   ```
   
   Edita `application.properties`:
   ```properties
   # Base de datos PostgreSQL
   spring.datasource.url=jdbc:postgresql://localhost:5432/taskflow_db
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña
   
   # JPA
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   
   # JWT (genera una clave segura)
   jwt.secret=tu_clave_secreta_muy_larga_y_segura
   jwt.expiration=86400000
   
   # Server
   server.port=8080
   ```

4. **Ejecutar la aplicación**
   ```bash
   ./mvnw spring-boot:run
   ```
   
   O en Windows:
   ```bash
   mvnw.cmd spring-boot:run
   ```

5. **Acceder a la documentación**
   
   Una vez iniciada la aplicación, accede a:
   - 📖 Swagger UI: `http://localhost:8080/swagger-ui.html`
   - 📄 OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## 📚 Endpoints

### 🔐 Autenticación (`/api/v1/auth`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/register` | Registrar nuevo usuario |
| `POST` | `/login` | Iniciar sesión |
| `GET` | `/me` | Obtener usuario actual |

### 📁 Proyectos (`/api/v1/projects`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/` | Crear proyecto |
| `GET` | `/all` | Obtener mis proyectos |
| `GET` | `/{projectId}` | Obtener proyecto por ID |
| `PUT` | `/{projectId}` | Actualizar proyecto |
| `DELETE` | `/{projectId}` | Eliminar proyecto |
| `POST` | `/{projectId}` | Agregar miembro |
| `DELETE` | `/{projectId}/` | Remover miembro |
| `PUT` | `/transfer-project` | Transferir propiedad |

### ✅ Tareas (`/api/v1/tasks`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/` | Crear tarea |
| `GET` | `/` | Obtener mis tareas |
| `PUT` | `/{taskId}` | Actualizar tarea |
| `DELETE` | `/{taskId}` | Eliminar tarea |
| `PATCH` | `/status/{taskId}` | Cambiar estado |
| `PUT` | `/{taskId}/assign/{userId}` | Asignar tarea |
| `PUT` | `/{taskId}/unassign/{userId}` | Desasignar tarea |

## 📊 Modelos de Datos

### Usuario (User)
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "roles": ["USER"]
}
```

### Proyecto (Project)
```json
{
  "id": 1,
  "name": "Mi Proyecto",
  "description": "Descripción del proyecto",
  "owner": { ... },
  "members": [ ... ]
}
```

### Tarea (Task)
```json
{
  "id": 1,
  "title": "Implementar feature",
  "description": "Descripción de la tarea",
  "status": "TO_DO",
  "priority": "HIGH",
  "dueDate": "2026-02-01T10:00:00",
  "project": { ... },
  "assignedTo": { ... }
}
```

### Estados de Tarea
| Estado | Descripción |
|--------|-------------|
| `TO_DO` | Pendiente |
| `IN_PROGRESS` | En progreso |
| `DONE` | Completada |

### Prioridades de Tarea
| Prioridad | Descripción |
|-----------|-------------|
| `LOW` | Baja |
| `MEDIUM` | Media |
| `HIGH` | Alta |

## 🔒 Autenticación

La API utiliza **JWT (JSON Web Tokens)** para la autenticación. Para acceder a los endpoints protegidos:

1. **Registrar/Iniciar sesión** para obtener el token
2. **Incluir el token** en el header de las peticiones:
   ```
   Authorization: Bearer <tu_token_jwt>
   ```

### Ejemplo de uso con cURL

```bash
# Registrar usuario
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"john","email":"john@example.com","password":"password123"}'

# Login
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john@example.com","password":"password123"}'

# Crear proyecto (con token)
curl -X POST http://localhost:8080/api/v1/projects \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <tu_token>" \
  -d '{"name":"Mi Proyecto","description":"Descripción"}'
```

## 🏗 Arquitectura del Proyecto

```
src/main/java/com/TaskFlow/TaskFlow/
├── 📄 TaskFlowApplication.java      # Clase principal
├── 🎮 controller/                   # Controladores REST
│   ├── AuthController.java
│   ├── ProjectController.java
│   └── TaskController.java
├── 📦 dto/                          # Data Transfer Objects
│   ├── request/                     # DTOs de entrada
│   └── response/                    # DTOs de salida
├── 🗃 entity/                        # Entidades JPA
│   ├── User.java
│   ├── Project.java
│   ├── Task.java
│   └── ...
├── ⚠️ exception/                    # Manejo de excepciones
│   ├── GlobalExceptionHandler.java
│   └── ...
├── 🔄 mapper/                       # Mappers Entity <-> DTO
├── 📚 repository/                   # Repositorios JPA
├── 🔐 security/                     # Configuración de seguridad
│   ├── SecurityConfig.java
│   ├── JwtService.java
│   └── ...
└── ⚙️ service/                      # Lógica de negocio
    ├── AuthService.java
    ├── ProjectService.java
    └── TaskService.java
```

## 🧪 Testing

Ejecutar las pruebas:

```bash
# Todas las pruebas
./mvnw test

# Con cobertura
./mvnw test jacoco:report
```

## 🐳 Docker (Opcional)

```dockerfile
# Dockerfile
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

```yaml
# docker-compose.yml
version: '3.8'
services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/taskflow_db
    depends_on:
      - db
  
  db:
    image: postgres:15-alpine
    environment:
      - POSTGRES_DB=taskflow_db
      - POSTGRES_USER=taskflow
      - POSTGRES_PASSWORD=taskflow123
    volumes:
      - postgres_data:/var/lib/postgresql/data

volumes:
  postgres_data:
```

## 🤝 Contribuir

1. Fork el proyecto
2. Crea tu rama de feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request


## 👥 Autores

- **Luis Angel Rodriguez** - *Desarrollo inicial* - [@LuisAngel832](https://github.com/LuisAngel832)

## 🙏 Agradecimientos

- Spring Boot Team
- Comunidad Open Source

---

<p align="center">
  Hecho con ❤️ usando Spring Boot
</p>

<p align="center">
  <a href="#-taskflow-api">⬆️ Volver arriba</a>
</p>
