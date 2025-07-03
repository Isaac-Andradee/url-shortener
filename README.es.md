# Short4Us: Un Acortador de URL con Spring Boot

[![GitHub Stars](https://img.shields.io/github/stars/Isaac-Andradee/short4us.svg)](https://github.com/Isaac-Andradee/short4us/stargazers)
[![GitHub Issues](https://img.shields.io/github/issues/Isaac-Andradee/short4us.svg)](https://github.com/Isaac-Andradee/short4us/issues)
[![Current Version](https://img.shields.io/github/v/release/Isaac-Andradee/short4us)](https://github.com/Isaac-Andradee/short4us/releases)
[![Build Status](https://img.shields.io/github/actions/workflow/status/Isaac-Andradee/short4us/.github%2Fworkflows%2Fmaven-build-main.yml)](https://github.com/Isaac-Andradee/short4us/actions/workflows/maven-build-main.yml)
[![Contributions](https://img.shields.io/github/contributors/Isaac-Andradee/short4us.svg)](https://github.com/Isaac-Andradee/short4us/graphs/contributors)
[![Top Language](https://img.shields.io/github/languages/top/Isaac-Andradee/short4us)](https://github.com/search?q=repo%3AIsaac-Andradee%2Fshort4us++language%3AJava&type=code)

Short4Us es un sistema de acortamiento de URLs desarrollado con Spring Boot y arquitectura de microservicios. El sistema es totalmente escalable utilizando Docker Swarm, permitiéndote acortar URLs largas de forma eficiente y confiable.

![Screenshot](https://github.com/user-attachments/assets/d419bea8-d448-4e3a-b950-19a833c06f82)

## Arquitectura

Short4Us consta de varios microservicios que trabajan juntos:

1. **Servicio Shortener**: Responsable de acortar URLs largas, generando claves cortas.
2. **Servicio Resolver**: Redirige a los usuarios desde URLs cortas a las URLs originales.
3. **Servicio KeyGen**: Genera claves únicas para las URLs acortadas.
4. **Servidor Eureka**: Servicio de descubrimiento para registrar y localizar microservicios.
5. **API Gateway**: Puerta de enlace para enrutar solicitudes a los servicios correspondientes.

La aplicación utiliza Redis para almacenamiento en caché de alto rendimiento y MongoDB Atlas para almacenamiento persistente, diseñada para escalar horizontalmente.

> [!TIP]
> Puedes ver el flujo de diseño del sistema y servicios [aquí](https://github.com/Isaac-Andradee/short4us/blob/main/SYSTEM-DESIGN.md)

## Requisitos Previos

Para ejecutar Short4Us, necesitas:

- Docker y Docker Compose
- Docker Swarm
- Cuenta y clúster en MongoDB Atlas
- Cuenta de Docker Hub (para subir imágenes personalizadas)
- Java 21 y Maven 3.6+ (para compilar la aplicación)

## Configuración de MongoDB Atlas

Antes de desplegar la aplicación, necesitas configurar un clúster de MongoDB Atlas:

1. **Crear una cuenta en MongoDB Atlas**: Ve a [MongoDB Atlas](https://www.mongodb.com/atlas) y crea una cuenta.

2. **Crear un clúster**:

   - Crea un nuevo clúster (el nivel gratuito M0 es suficiente para pruebas)
   - Elige tu proveedor de nube y región preferidos
   - Nombra tu clúster (por ejemplo, "short4us-cluster")

3. **Configurar el acceso a la base de datos**:

   - Ve a "Database Access" en el panel de Atlas
   - Crea un usuario de base de datos con permisos de lectura/escritura
   - Anota el nombre de usuario y contraseña

4. **Configurar el acceso de red**:

- Ve a "Network Access" en el panel de Atlas
- Agrega tu dirección IP o `0.0.0.0/0` para pruebas (no recomendado en producción)

5. **Obtener la cadena de conexión**:

- Ve a "Clusters" y haz clic en "Connect"
- Elige "Connect your application"
- Copia la cadena de conexión (parecerá algo como: `mongodb+srv://username:password@cluster.xxxxx.mongodb.net/`)

## Variables de Entorno

La aplicación depende de variables de entorno para su configuración. Crea un archivo `.env` en la raíz del proyecto con las siguientes variables:

```
# Configuración de Eureka
EUREKA_SERVER_HOST=eureka-server
EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-server:8761/eureka/
EUREKA_SERVER_PORT=8761

# Configuración de MongoDB
MONGODB_URI=mongodb+srv://username:password@cluster.xxxxx.mongodb.net/short4us?retryWrites=true&w=majority
MONGODB_DATABASE=short4us
MONGO_USERNAME=username
MONGO_PASSWORD=password

# Configuración de Redis
REDIS_NODE_1=redis-node-1:6379
REDIS_NODE_2=redis-node-2:6379
REDIS_NODE_3=redis-node-3:6379
REDIS_NODE_4=redis-node-4:6379
REDIS_NODE_5=redis-node-5:6379
REDIS_NODE_6=redis-node-6:6379
REDIS_PORT=6379
REDIS_PASSWORD=
REDIS_TIMEOUT=6000
SPRING_CACHE_TYPE=redis

# Puertos de Servicios
SHORTENER_SERVICE_PORT=8080
RESOLVER_SERVICE_PORT=8083
KEYGEN_SERVICE_PORT=8081

# Configuración del Dominio
SHORT_DOMAIN=http://localhost/
```

> [!IMPORTANT]
> Reemplaza el `MONGODB_URI` con tu cadena de conexión real de MongoDB Atlas, incluyendo tu nombre de usuario, contraseña y detalles del clúster.

## Guía de Instalación

### 1. Clonar el Repositorio

```bash
git clone https://github.com/Isaac-Andradee/short4us.git
cd short4us
```

### 2. Compilar la Aplicación

Compila todos los microservicios usando Maven:

```bash
mvn clean package
```

### 3. Crear y Subir Imágenes Docker

Necesitas construir tus propias imágenes Docker y subirlas a Docker Hub:

```bash
# Iniciar sesión en Docker Hub
docker login

# Crear y etiquetar imágenes (reemplaza 'yourusername' con tu usuario de Docker Hub)
docker build -t yourusername/eureka:latest ./eureka
docker build -t yourusername/shortener-service ./shortener-service
docker build -t yourusername/resolver-service ./resolver-service
docker build -t yourusername/keygen-service ./keygen-service

# Subir imágenes a Docker Hub
docker push yourusername/eureka:latest
docker push yourusername/shortener-service:latest
docker push yourusername/resolver-service:latest
docker push yourusername/keygen-service:latest
```

### 4. Actualizar stack.yml

Después de subir tus imágenes, actualiza el archivo `stack.yml` para usar tus imágenes de Docker Hub:

```yaml
# Reemplaza todas las apariciones del digest sha256 con tu imagen de Docker Hub
# Ejemplo:
eureka-server:
  image: yourusername/eureka:latest
  # ... resto de la configuración

shortener-service:
  image: yourusername/shortener-service:latest
  # ... resto de la configuración

resolver-service:
  image: yourusername/resolver-service:latest
  # ... resto de la configuración

keygen-service:
  image: yourusername/keygen-service:latest
  # ... resto de la configuración
```

### 5. Configuración del Entorno

Crea un archivo `.env` en la raíz del proyecto con las variables de entorno listadas arriba, asegurándote de configurar correctamente la cadena de conexión de MongoDB Atlas.

### 6. Despliegue con Docker Swarm

```bash
# # Inicializar Docker Swarm (si no está configurado)
docker swarm init

# Desplegar el stack
docker stack deploy -c stack.yml short4us

# IMPORTANTE: Después del despliegue, inicializa el clúster de Redis conectándote a uno de los nodos Redis y ejecutando:
docker exec -it $(docker ps | grep redis-node-1 | awk '{print $1}') sh -c "redis-cli --cluster create redis-node-1:6379 redis-node-2:6379 redis-node-3:6379 redis-node-4:6379 redis-node-5:6379 redis-node-6:6379 --cluster-replicas 1"
```

### 7. Verificar el Despliegue

Verifica que todos los servicios estén corriendo:

```bash
docker service ls
```

Deberías ver todos los servicios (eureka-server, shortener-service, resolver-service, keygen-service y nodos redis) ejecutándose.

## Cómo Usar

Short4Us proporciona una API REST para acortar URLs:

### Acortar una URL

La aplicación corre por defecto en `http://localhost:80/` pero puedes probarla en Postman o en la terminal:

```bash
curl -X POST http://localhost/shorten \
  -H "Content-Type: application/json" \
  -d '{"longUrl":"https://www.example.com/some/very/long/url/that/you/want/to/shorten", "alias": "optional-custom-alias"}'
```

### Acceder a una URL Acortada

Simplemente accede a la URL corta en un navegador:

```
http://localhost/{short-key}
```

El servicio redirigirá automáticamente a la URL original.

## Arquitectura Técnica

- **Spring Boot**: Framework base para todos los servicios
- **Spring Cloud**: Para configuración distribuida y descubrimiento de servicios
- **MongoDB Atlas**: Base de datos en la nube para almacenamiento persistente
- **Redis Cluster**: Caché distribuida de alto rendimiento
- **Nginx**: Como puerta de enlace API para el enrutamiento de solicitudes
- **Docker & Docker Swarm**: Para contenerización y orquestación

## Contribuir

¡Las contribuciones son bienvenidas! Para contribuir:

1. Haz un fork del repositorio
2. Crea tu rama de funcionalidad (`git checkout -b feature/amazing-feature`)
3. Haz commit de tus cambios (`git commit -m 'Add an amazing feature'`)
4. Haz push a la rama (`git push origin feature/amazing-feature`)
5. Abre un Pull Request

## Contacto

Isaac Andrade - [@Isaac-Andradee](mailto:isaac.andra84@gmail.com)
