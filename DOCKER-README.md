# Aplicación Accenture - Guía Docker

Esta guía te ayudará a ejecutar la aplicación Spring Boot con Docker y Docker Compose.

## 📋 Prerrequisitos

- Docker Desktop instalado
- Docker Compose (incluido con Docker Desktop)
- Al menos 4GB de RAM disponibles

## 🚀 Inicio Rápido

### Método 1: Usando el script de gestión (Recomendado)

```bash
# Dar permisos de ejecución al script
chmod +x docker-manager.sh

# Construir y iniciar todos los servicios
./docker-manager.sh build
./docker-manager.sh start
```

### Método 2: Usando Docker Compose directamente

```bash
# Construir e iniciar todos los servicios
docker-compose up --build -d
```

## 📊 Servicios Disponibles

Una vez iniciados los servicios, tendrás acceso a:

| Servicio | URL | Descripción |
|----------|-----|-------------|
| **Aplicación Principal** | http://localhost:8080 | API REST de la aplicación |
| **MongoDB Admin** | http://localhost:8081 | Interfaz web para MongoDB |

## 🛠️ Comandos del Script de Gestión

```bash
./docker-manager.sh [comando]
```

### Comandos disponibles:

- `build` - Construir la imagen Docker
- `start` - Iniciar todos los servicios
- `stop` - Detener todos los servicios
- `restart` - Reiniciar todos los servicios
- `logs` - Mostrar logs de la aplicación
- `status` - Mostrar estado de los servicios
- `shell` - Conectar al contenedor de la aplicación
- `test` - Ejecutar tests de la aplicación
- `clean` - Limpiar contenedores e imágenes
- `help` - Mostrar ayuda

## 🔧 Comandos Docker Compose

### Iniciar servicios
```bash
docker-compose up -d
```

### Ver logs
```bash
# Todos los servicios
docker-compose logs -f

# Solo la aplicación
docker-compose logs -f accenture-app
```

### Detener servicios
```bash
docker-compose down
```

### Reconstruir y reiniciar
```bash
docker-compose up --build -d
```

### Limpiar todo (incluyendo volúmenes)
```bash
docker-compose down -v
docker system prune -f
```

## 🗄️ Bases de Datos

### MongoDB
- **Host:** localhost:27017
- **Usuario:** admin
- **Contraseña:** password
- **Base de datos:** accenture
- **Admin UI:** http://localhost:8081

### Conectar al contenedor
```bash
./docker-manager.sh shell
# o
docker-compose exec accenture-app /bin/bash
```

### Ver logs en tiempo real
```bash
./docker-manager.sh logs
# o
docker-compose logs -f accenture-app
```

## 🧪 Testing

### Ejecutar tests dentro del contenedor
```bash
./docker-manager.sh test
# o
docker-compose exec accenture-app mvn test
```

## 🐛 Solución de Problemas

### La aplicación no inicia
1. Verifica que MongoDB y Redis estén ejecutándose:
   ```bash
   ./docker-manager.sh status
   ```

2. Revisa los logs:
   ```bash
   ./docker-manager.sh logs
   ```

### Error de conexión a MongoDB
1. Verifica que MongoDB esté ejecutándose:
   ```bash
   docker-compose ps mongodb
   ```

2. Revisa los logs de MongoDB:
   ```bash
   docker-compose logs mongodb
   ```

### Limpiar todo y empezar de cero
```bash
./docker-manager.sh clean
./docker-manager.sh build
./docker-manager.sh start
```

### Puerto ya en uso
Si algún puerto está en uso, puedes cambiarlos en el archivo `docker-compose.yml`:

```yaml
ports:
  - "8081:8080"  # Cambiar el primer número
```

## 📁 Estructura de Archivos Docker

```
.
├── Dockerfile                 # Imagen multi-stage de la aplicación
├── docker-compose.yml        # Orquestación de servicios
├── .dockerignore             # Archivos a ignorar en la imagen
├── docker-manager.sh         # Script de gestión
└── src/main/resources/
    └── application-docker.properties  # Configuración para Docker
```

## 🔒 Seguridad

- La aplicación se ejecuta con un usuario no-root
- Las contraseñas están configuradas para desarrollo local
- **IMPORTANTE:** Cambiar las credenciales para producción

## 📝 Variables de Entorno

Puedes personalizar la configuración creando un archivo `.env`:

```env
MONGO_USER=admin
MONGO_PASSWORD=your_password
MONGO_DB=accenture
```

