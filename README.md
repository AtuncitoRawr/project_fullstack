**Perfulandia SPA**
Para la asignatura de Desarrolo Fullstack I se nos pidió el crear un sistema de gestión basado en microservicios para la compañia "Perfulandia SPA",
a continuacion se muestra cada paso que se siguió durante el proyecto.

[![Licencia MIT](https://img.shields.io/badge/licencia-MIT-blue)](LICENSE)
[![Estado](https://img.shields.io/badge/estado-en%20desarrollo-yellow)](https://github.com/AtuncitoRawr/project_fullstack)

# 🛒 Perfulandia SPA - Sistema de Microservicios 

---

## 📌 Tabla de Contenidos
- [Descripción](#-descripción)
- [Características](#-características)
- [Arquitectura](#-arquitectura)
- [Instalación](#-instalación)
- [Uso](#-uso)
- [Pruebas](#-pruebas)
- [Tecnologías](#-tecnologías)
- [Equipo](#-equipo)
- [Licencia](#-licencia)

---

## 🚀 Descripción
Proyecto académico desarrollado para **DUOC UC** (curso Desarrollo Fullstack I) que implementa microservicios con Spring Boot para resolver:
- **Escalabilidad**: Servicios independientes que crecen según demanda.
- **Mantenibilidad**: Actualizaciones sin afectar el sistema completo.
- **Rendimiento**: Optimización por módulo (inventario, usuarios, pedidos).

**Histórias de usuario implementadas**:
- Gestión de inventario (HU-003, HU-004).
- Registro de ventas (HU-005).
- Reseñas de clientes (HU-008).

---

## ✨ Características
### Microservicios Implementados
- **Inventario**: Gestión de productos (CRUD completo).
- **Usuarios**: *(CRUD completo)*.
- **Pedidos**: *(CRUD completo)*.
- **Carrito**: *(CRUD completo)*.

### Base de Datos
- **Laragoon** (configurable con PostgreSQL/MySQL).
- Modelo relacional para datos estructurados.

---

## 🏗️ Arquitectura
### Diagramas Clave
| Diagrama | Descripción |
|----------|-------------|
| ![Casos de Uso y de clases] | Roles y funcionalidades |
| ![Despliegue] | Infraestructura cloud/on-premise |

**Patrones aplicados**:
- **Strangler Pattern**: Migración gradual desde el monolito.
- **MVC**: Separación clara de capas.

---

## ⚙️ Instalación
1. **Clonar repositorio**:
   ```bash
   git clone https://github.com/AtuncitoRawr/project_fullstack.git
   ```
2. **Configurar Laragoon**:
   ```bash
   laragoon init --project=perfulandia
   ```
3. **Ejecutar microservicio de Inventario**:
   ```bash
   cd inventario-ms
   mvn spring-boot:run
   ```

---

## 🖥️ Uso
### Endpoints (Inventario)
| Método | Ruta | Descripción |
|--------|------|-------------|
| `GET` | `/api/productos` | Listar todos |
| `POST` | `/api/productos` | Crear nuevo |
| `PUT` | `/api/productos/{id}` | Actualizar |
|`DELETE`|`/api/productos/{id}`|Borrar algun producto|

**Ejemplo con Postman**:
![Ejemplo POST](media/image11.png)

---


## 🧪 Pruebas
## 🧪 Pruebas y Documentación Técnica

### ✅ Pruebas Unitarias

Durante el desarrollo de los microservicios se realizaron pruebas unitarias para asegurar el correcto funcionamiento de los controladores y servicios. Se utilizaron las siguientes herramientas:

- **JUnit**: Para la creación y ejecución de las pruebas unitarias.
- **Spring MockMvc**: Para simular peticiones HTTP a los controladores sin necesidad de desplegar el servidor.
- **Jackson ObjectMapper**: Para la serialización y deserialización de objetos Java a JSON y viceversa durante las pruebas.

Las pruebas incluyeron distintos escenarios como:

- Búsqueda de recursos por ID.
- Validación de respuestas correctas para recursos inexistentes.
- Confirmación de los códigos de estado HTTP esperados.

Todos los tests se ejecutaron exitosamente, verificando el comportamiento esperado de cada endpoint.

---

### 📖 Documentación OpenAPI (Swagger)

Se integró **Springdoc OpenAPI** para la documentación de la API REST. Esto permite una visualización interactiva de todos los endpoints disponibles, sus parámetros, respuestas y métodos HTTP.

La documentación está disponible mientras el servicio está corriendo en el siguiente enlace:

- **Swagger UI**:  
`http://localhost:8080/swagger-ui/index.html`

Esto facilita la consulta, prueba y validación de los servicios expuestos por los microservicios.

---
### 🔗 HATEOAS - Navegabilidad de la API

Siguiendo el principio **HATEOAS (Hypermedia as the Engine of Application State)**, las respuestas de los endpoints incluyen enlaces relevantes que permiten la navegación entre recursos relacionados.

Ejemplo de una respuesta típica de la API para un usuario con ID `3`:

```json
{
  "_links": {
    "self": {
      "href": "http://localhost:8080/api/v1/usuarios/3"
    },
    "usuarios": {
      "href": "http://localhost:8080/api/v1/usuarios"
    },
    "eliminar": {
      "href": "http://localhost:8080/api/v1/usuarios/3"
    },
    "actualizar": {
      "href": "http://localhost:8080/api/v1/usuarios/3"
    }
  }
}
```
## 🛠 Tecnologías
| Categoría | Tecnologías |
|-----------|-------------|
| Backend | Spring Boot, JPA, Maven |
| BD | Laragoon (PostgreSQL/MySQL) |
| DevOps | GitHub, Trello, Discord |

---

## 👥 Equipo
| Nombre | GitHub | Rol |
|--------|--------|-----|
| Nicolás Bozzo | [@AtuncitoRawr](https://github.com/AtuncitoRawr) | QA, Backend |
| Mateo Díaz | [@PentaMateo](https://github.com/PentaMateo) | QA,Backend |
| Benjamín Ramírez | [@BenjaRami](https://github.com/BenjaRami) | Documentación |
| Cristóbal Cruz | [@04kryzz](https://github.com/04kryzz) | QA,Backend |

**Profesores**: Rubén Badilla, Emilio Soto (DUOC UC).

---

## 📜 Licencia
MIT © 2025 - Equipo Perfulandia.  
*Documentación académica para DUOC UC.*

---

## 🔗 Enlaces Relevantes
- [Trello](https://trello.com/b/paFBHqLO/proyecto-fullstack-perfulandia)
- [Repositorio](https://github.com/AtuncitoRawr/project_fullstack)
```

## 🔗 Enlaces Relevantes
- [Trello](https://trello.com/b/paFBHqLO/proyecto-fullstack-perfulandia)
- [Repositorio](https://github.com/AtuncitoRawr/project_fullstack)
```
