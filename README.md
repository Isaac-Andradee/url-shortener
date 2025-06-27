# Short4Us: A URL Shortener with Spring Boot

[![GitHub Stars](https://img.shields.io/github/stars/Isaac-Andradee/short4us.svg)](https://github.com/Isaac-Andradee/short4us/stargazers)
[![GitHub Issues](https://img.shields.io/github/issues/Isaac-Andradee/short4us.svg)](https://github.com/Isaac-Andradee/short4us/issues)
[![Current Version](https://img.shields.io/badge/version-0.1.0-green.svg)](https://github.com/Isaac-Andradee/short4us)
[![Build Status](https://img.shields.io/github/actions/workflow/status/Isaac-Andradee/short4us/.github%2Fworkflows%2Fmaven-build-main.yml)](https://github.com/Isaac-Andradee/short4us/actions/workflows/maven-build-main.yml)
[![Contributions](https://img.shields.io/github/contributors/Isaac-Andradee/short4us.svg)](https://github.com/Isaac-Andradee/short4us/graphs/contributors)
![In Development](https://img.shields.io/badge/status-in_development-yellow)
[![Top Language](https://img.shields.io/github/languages/top/Isaac-Andradee/short4us)](https://github.com/search?q=repo%3AIsaac-Andradee%2Fshort4us++language%3AJava&type=code)

Short4Us is a URL shortening system developed with Spring Boot and microservice architecture. The system is fully scalable using Docker Swarm, allowing you to shorten long URLs efficiently and reliably.

![Screenshot](https://github.com/user-attachments/assets/d419bea8-d448-4e3a-b950-19a833c06f82)

## Architecture

Short4Us consists of several microservices working together:

1. **Shortener Service**: Responsible for shortening long URLs, generating short keys.
2. **Resolver Service**: Redirects users from short URLs to the original URLs.
3. **KeyGen Service**: Generates unique keys for shortened URLs.
4. **Eureka Server**: Discovery service for registering and locating microservices.
5. **API Gateway**: Gateway for routing requests to the appropriate services.

The application uses Redis for high-performance caching and MongoDB Atlas for persistent storage, designed to scale horizontally.

## Prerequisites

To run Short4Us, you'll need:

- Docker and Docker Compose
- Docker Swarm
- MongoDB Atlas account and cluster
- Docker Hub account (for pushing custom images)
- Java 21 and Maven 3.6+ (for building the application)

## MongoDB Atlas Setup

Before deploying the application, you need to set up a MongoDB Atlas cluster:

1. **Create a MongoDB Atlas Account**: Go to [MongoDB Atlas](https://www.mongodb.com/atlas) and create an account.

2. **Create a Cluster**:
    - Create a new cluster (M0 free tier is sufficient for testing)
    - Choose your preferred cloud provider and region
    - Name your cluster (e.g., "short4us-cluster")

3. **Configure Database Access**:
    - Go to "Database Access" in the Atlas dashboard
    - Create a database user with read/write permissions
    - Note down the username and password

4. **Configure Network Access**:
    - Go to "Network Access" in the Atlas dashboard
    - Add your IP address or `0.0.0.0/0` for testing (not recommended for production)

5. **Get Connection String**:
    - Go to "Clusters" and click "Connect"
    - Choose "Connect your application"
    - Copy the connection string (it will look like: `mongodb+srv://username:password@cluster.xxxxx.mongodb.net/`)

## Environment Variables

The application relies on environment variables for configuration. Create a `.env` file in the project root with the following variables:

```
# Eureka Configuration
EUREKA_SERVER_HOST=eureka-server
EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-server:8761/eureka/
EUREKA_SERVER_PORT=8761

# MongoDB Configuration
MONGODB_URI=mongodb+srv://username:password@cluster.xxxxx.mongodb.net/short4us?retryWrites=true&w=majority
MONGODB_DATABASE=short4us
MONGO_USERNAME=username
MONGO_PASSWORD=password

# Redis Configuration
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

# Service Ports
SHORTENER_SERVICE_PORT=8080
RESOLVER_SERVICE_PORT=8083
KEYGEN_SERVICE_PORT=8081

# Domain Configuration
SHORT_DOMAIN=http://localhost/
```

> [!IMPORTANT]
> Replace the `MONGODB_URI` with your actual MongoDB Atlas connection string, including your username, password, and cluster details.

## Installation Guide

### 1. Clone the Repository

```bash
git clone https://github.com/Isaac-Andradee/short4us.git
cd short4us
```

### 2. Build the Application

Build all microservices using Maven:

```bash
mvn clean package
```

### 3. Build and Push Docker Images

You'll need to build your own Docker images and push them to Docker Hub:

```bash
# Login to Docker Hub
docker login

# Build and tag images (replace 'yourusername' with your Docker Hub username)
docker build -t yourusername/eureka:latest ./eureka
docker build -t yourusername/shortener-service:latest ./shortener-service
docker build -t yourusername/resolver-service:latest ./resolver-service
docker build -t yourusername/keygen-service:latest ./keygen-service

# Push images to Docker Hub
docker push yourusername/eureka:latest
docker push yourusername/shortener-service:latest
docker push yourusername/resolver-service:latest
docker push yourusername/keygen-service:latest
```

### 4. Update stack.yml

After pushing your images, update the `stack.yml` file to use your Docker Hub images:

```yaml
# Replace all occurrences of 'isaacandra' with your Docker Hub username
# Example:
eureka-server:
  image: yourusername/eureka:latest
  # ... rest of configuration

shortener-service:
  image: yourusername/shortener-service:latest
  # ... rest of configuration

resolver-service:
  image: yourusername/resolver-service:latest
  # ... rest of configuration

keygen-service:
  image: yourusername/keygen-service:latest
  # ... rest of configuration
```

### 5. Environment Setup

Create a `.env` file in the project root with the environment variables listed above, ensuring you configure the MongoDB Atlas connection string correctly.

### 6. Docker Swarm Deployment

```bash
# Initialize Docker Swarm (if not already configured)
docker swarm init

# Deploy the stack
docker stack deploy -c stack.yml short4us

# IMPORTANT: After deployment, initialize the Redis cluster by connecting to one of the Redis nodes and running:
docker exec -it $(docker ps | grep redis-node-1 | awk '{print $1}') sh -c "redis-cli --cluster create redis-node-1:6379 redis-node-2:6379 redis-node-3:6379 redis-node-4:6379 redis-node-5:6379 redis-node-6:6379 --cluster-replicas 1"
```

### 7. Verify Deployment

Check that all services are running:

```bash
docker service ls
```

You should see all services (eureka-server, shortener-service, resolver-service, keygen-service, and redis nodes) running.

## How to Use

Short4Us provides a REST API for shortening URLs:

### Shorten a URL

The application runs by default at `http://localhost:80/` but you can test it in postman or in the terminal:

```bash
curl -X POST http://localhost/shorten \
  -H "Content-Type: application/json" \
  -d '{"longUrl":"https://www.example.com/some/very/long/url/that/you/want/to/shorten", "alias": "optional-custom-alias"}'
```

### Access Shortened URL

Simply access the short URL in a browser:

```
http://localhost/{short-key}
```

The service will automatically redirect to the original URL.

## Technical Architecture

- **Spring Boot**: Base framework for all services
- **Spring Cloud**: For distributed configuration and service discovery
- **MongoDB Atlas**: Cloud-hosted database for persistent storage
- **Redis Cluster**: Distributed cache for high performance
- **Nginx**: As API Gateway for request routing
- **Docker & Docker Swarm**: For containerization and orchestration

## Contributing

Contributions are welcome! To contribute:

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add an amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## Contact

Isaac Andrade - [@Isaac-Andradee](isaac.andra84@gmail.com)
