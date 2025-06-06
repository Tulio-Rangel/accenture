# 🏪 Sistema de Gestión de Franquicias - Accenture

## 📋 Descripción

Sistema de gestión de franquicias desarrollado con **Spring Boot 3.5** y **Java 17** que permite administrar franquicias, sus sucursales y productos.

### 🎯 Funcionalidades Principales

- ✅ **Gestión de Franquicias**: Crear, listar y actualizar franquicias
- ✅ **Gestión de Sucursales**: Agregar y administrar sucursales por franquicia
- ✅ **Gestión de Productos**: Añadir, eliminar y actualizar productos en sucursales
- ✅ **Control de Stock**: Gestión completa del inventario de productos
- ✅ **Análisis de Productos**: Obtener productos con mayor stock por sucursal
- ✅ **API RESTful Reactiva**: Endpoints no bloqueantes con WebFlux
- ✅ **Validación de Datos**: Validaciones automáticas con Bean Validation

## 🏗️ Arquitectura

### Stack Tecnológico

- **Java 17**
- **Spring Boot 3.5.0**
- **Spring WebFlux** (Programación Reactiva)
- **Spring Data MongoDB Reactive**
- **MongoDB 7.0** (Base de datos principal)
- **Docker & Docker Compose**

### Patrón Arquitectónico

```
┌─────────────────┐
│   Presentation  │  ← Controllers (API REST)
│     Layer       │
└─────────────────┘
         │
┌─────────────────┐
│   Application   │  ← Services
│     Layer       │
└─────────────────┘
         │
┌─────────────────┐
│     Domain      │  ← Entities, Ports
│     Layer       │
└─────────────────┘
         │
┌─────────────────┐
│ Infrastructure  │  ← Repositories, Adapters & Config
│     Layer       │
└─────────────────┘
```

## 🚀 Despliegue Local con Docker

### Prerrequisitos

- Docker Desktop instalado
- Docker Compose (incluido con Docker Desktop)
- Al menos 4GB de RAM disponibles

### Método 1: Script de Gestión (Recomendado)

```bash
# 1. Clonar el repositorio
git clone <repository-url>
cd accenture

# 2. Dar permisos de ejecución al script
chmod +x docker-manager.sh

# 3. Construir la aplicación
./docker-manager.sh build

# 4. Iniciar todos los servicios
./docker-manager.sh start

# 5. Verificar que todo esté funcionando
./docker-manager.sh status
```

### Método 2: Docker Compose Directo

```bash
# Construir e iniciar todos los servicios
docker-compose up --build -d

# Ver logs en tiempo real
docker-compose logs -f accenture-app

# Detener servicios
docker-compose down
```

### 🌐 URLs de Acceso

Una vez desplegada la aplicación, tendrás acceso a:

| Servicio | URL | Descripción |
|----------|-----|-------------|
| **API Principal** | http://localhost:8080 | API REST de la aplicación |
| **Health Check** | http://localhost:8080/actuator/health | Estado de la aplicación |
| **MongoDB Admin** | http://localhost:8081 | Interfaz web para MongoDB |

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api/v1
```

### Endpoints Disponibles

#### 🏢 Franquicias

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/franchises` | Crear nueva franquicia |
| `GET` | `/franchises` | Listar todas las franquicias |
| `GET` | `/franchises/{franchiseId}` | Obtener franquicia por ID |
| `PUT` | `/franchises/{franchiseId}/name` | Actualizar nombre de franquicia |

#### 🏪 Sucursales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/franchises/{franchiseId}/branches` | Agregar sucursal a franquicia |
| `PUT` | `/franchises/{franchiseId}/branches/{branchId}/name` | Actualizar nombre de sucursal |

#### 📦 Productos

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/franchises/{franchiseId}/branches/{branchId}/products` | Agregar producto a sucursal |
| `PUT` | `/franchises/{franchiseId}/branches/{branchId}/products/{productId}/name` | Actualizar nombre de producto |
| `PUT` | `/franchises/{franchiseId}/branches/{branchId}/products/{productId}/stock` | Actualizar stock de producto |
| `DELETE` | `/franchises/{franchiseId}/branches/{branchId}/products/{productId}` | Eliminar producto |

#### 📊 Análisis

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/franchises/{franchiseId}/top-products` | Obtener productos con mayor stock por sucursal |

### 📋 Modelos de Datos

#### FranchiseDto
```json
{
  "id": "string",
  "name": "string",
  "branches": [BranchDto]
}
```

#### BranchDto
```json
{
  "id": "string",
  "name": "string",
  "products": [ProductDto]
}
```

#### ProductDto
```json
{
  "id": "string",
  "name": "string",
  "stock": integer
}
```

#### TopProductDto
```json
{
  "productName": "string",
  "branchName": "string",
  "stock": integer
}
```

## 🧪 Pruebas con Postman

### Importar Colección

1. **Abrir Postman**
2. **Importar la colección**: Archivo → Import → Seleccionar `Accenture.postman_collection.json`
3. **Crear Environment** (opcional):
   - Name: `Accenture Local`
   - Variables:
     - `baseUrl`: `http://localhost:8080`
     - `franchiseId`: (se auto-genera)
     - `branchId`: (se auto-genera)
     - `productId`: (se auto-genera)

### 🔄 Flujo de Pruebas Recomendado

#### 1. **Crear Franquicia**
```http
POST /api/v1/franchises
Content-Type: application/json

{
    "name": "Mi Franquicia"
}
```

#### 2. **Agregar Sucursal**
```http
POST /api/v1/franchises/{{franchiseId}}/branches
Content-Type: application/json

{
    "name": "Sucursal Centro"
}
```

#### 3. **Agregar Productos**
```http
POST /api/v1/franchises/{{franchiseId}}/branches/{{branchId}}/products
Content-Type: application/json

{
    "name": "Producto A",
    "stock": 100
}
```

#### 4. **Actualizar Stock**
```http
PUT /api/v1/franchises/{{franchiseId}}/branches/{{branchId}}/products/{{productId}}/stock
Content-Type: application/json

{
    "stock": 75
}
```

#### 5. **Obtener Top Products**
```http
GET /api/v1/franchises/{{franchiseId}}/top-products
```

### 📝 Variables Automáticas

La colección de Postman incluye scripts que automáticamente capturan y configuran:

- `franchiseId` - Después de crear una franquicia
- `branchId` - Después de agregar una sucursal
- `productId` - Después de agregar un producto

Esto permite ejecutar las pruebas de forma secuencial sin necesidad de copiar/pegar IDs manualmente.

## 🛠️ Comandos de Desarrollo

### Scripts Disponibles

```bash
# Ver todos los comandos disponibles
./docker-manager.sh help

# Construir la aplicación
./docker-manager.sh build

# Iniciar servicios
./docker-manager.sh start

# Ver logs en tiempo real
./docker-manager.sh logs

# Ver estado de servicios
./docker-manager.sh status

# Conectar al contenedor
./docker-manager.sh shell

# Ejecutar tests
./docker-manager.sh test

# Reiniciar servicios
./docker-manager.sh restart

# Limpiar todo
./docker-manager.sh clean
```

### Desarrollo Local (Sin Docker)

```bash
# Instalar dependencias
./mvnw clean install

# Ejecutar aplicación
./mvnw spring-boot:run

# Ejecutar tests
./mvnw test

# Generar JAR
./mvnw clean package
```

## 🗄️ Base de Datos

### MongoDB
- **Puerto**: 27017
- **Usuario**: admin
- **Contraseña**: password
- **Base de datos**: accenture
- **Colección**: franchises

## 🔍 Monitoreo

```

### Métricas
```bash
curl http://localhost:8080/actuator/metrics
```

### Logs
```bash
# Ver logs de la aplicación
docker-compose logs -f accenture-app

# Ver logs de MongoDB
docker-compose logs -f mongodb

# Ver logs de Redis
docker-compose logs -f redis
```

## 🐛 Solución de Problemas

### Problemas Comunes

#### Puerto ya en uso
```bash
# Verificar qué proceso usa el puerto
lsof -i :8080

# Cambiar puerto en docker-compose.yml
ports:
  - "8081:8080"  # Cambiar primer número
```

#### Error de conexión a MongoDB
```bash
# Verificar estado de MongoDB
docker-compose ps mongodb

# Ver logs de MongoDB
docker-compose logs mongodb

# Reiniciar MongoDB
docker-compose restart mongodb
```

#### Limpiar y reiniciar todo
```bash
./docker-manager.sh clean
./docker-manager.sh build
./docker-manager.sh start
```

### Logs y Debug

```bash
# Logs de la aplicación con más detalle
docker-compose logs -f --tail=100 accenture-app

# Conectar al contenedor para debug
./docker-manager.sh shell

# Verificar conectividad entre servicios
docker-compose exec accenture-app ping mongodb
```

## 📁 Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/tulio/accenture/
│   │   ├── application/
│   │   │   └── services/           # Servicios de aplicación
│   │   ├── domain/
│   │   │   ├── entities/           # Entidades de dominio
│   │   │   ├── exceptions/         # Excepciones de negocio
│   │   │   └── ports/             # Puertos (interfaces)
│   │   ├── infrastructure/
│   │   │   ├── adapters/          # Adaptadores (implementaciones)
│   │   │   └── configuration/     # Configuraciones
│   │   ├── shared/
│   │   │   └── dto/               # Data Transfer Objects
│   │   └── AccentureApplication.java
│   └── resources/
│       ├── application.properties
│       └── application-docker.properties
└── test/                          # Tests unitarios e integración
```

## 🔐 Seguridad

- ✅ Validación de entrada con Bean Validation
- ✅ Manejo de errores centralizado
- ✅ Usuario no-root en contenedores
- ✅ CORS configurado para desarrollo
- ⚠️ **Importante**: Cambiar credenciales para producción

## 📄 Licencia

Este proyecto es una prueba técnica para Accenture.

## 👥 Contacto

**Desarrollador**: Tulio Rangel Núñez  
**Email**: tuliorangeln@gmail.com  
**Fecha**: Junio 2025

---

### 🚀 ¡Listo para usar!

1. Ejecuta `./docker-manager.sh build && ./docker-manager.sh start`
2. Importa la colección de Postman `Accenture.postman_collection.json`
3. Comienza a probar la API con los endpoints documentados


