# Short4Us : Un Raccourcisseur d’URL avec Spring Boot

[![GitHub Stars](https://img.shields.io/github/stars/Isaac-Andradee/short4us.svg)](https://github.com/Isaac-Andradee/short4us/stargazers)
[![GitHub Issues](https://img.shields.io/github/issues/Isaac-Andradee/short4us.svg)](https://github.com/Isaac-Andradee/short4us/issues)
[![Current Version](https://img.shields.io/github/v/release/Isaac-Andradee/short4us)](https://github.com/Isaac-Andradee/short4us/releases)
[![Build Status](https://img.shields.io/github/actions/workflow/status/Isaac-Andradee/short4us/.github%2Fworkflows%2Fmaven-build-main.yml)](https://github.com/Isaac-Andradee/short4us/actions/workflows/maven-build-main.yml)
[![Contributions](https://img.shields.io/github/contributors/Isaac-Andradee/short4us.svg)](https://github.com/Isaac-Andradee/short4us/graphs/contributors)
[![Top Language](https://img.shields.io/github/languages/top/Isaac-Andradee/short4us)](https://github.com/search?q=repo%3AIsaac-Andradee%2Fshort4us++language%3AJava&type=code)

Short4Us est un système de raccourcissement d’URL développé avec Spring Boot et une architecture microservices. Le système est entièrement évolutif grâce à Docker Swarm, vous permettant de raccourcir des URL longues de manière efficace et fiable.

![Screenshot](https://github.com/user-attachments/assets/d419bea8-d448-4e3a-b950-19a833c06f82)

## Architecture

Short4Us se compose de plusieurs microservices qui travaillent ensemble :

1. **Shortener Service**: Responsable du raccourcissement des URL longues et de la génération de clés courtes.
2. **Resolver Service**: Redirige les utilisateurs des URL courtes vers les URL originales.
3. **KeyGen Service**: Génère des clés uniques pour les URL raccourcies.
4. **Eureka Server**: Service de découverte pour enregistrer et localiser les microservices.
5. **API Gateway**: Passerelle pour router les requêtes vers les services appropriés.

L’application utilise Redis pour la mise en cache haute performance et MongoDB Atlas pour le stockage persistant, conçue pour évoluer horizontalement.

> [!TIP]
> Vous pouvez consulter la conception système et des services [ici](https://github.com/Isaac-Andradee/short4us/blob/main/SYSTEM-DESIGN.md)

## Prérequis

Pour exécuter Short4Us, vous aurez besoin de :

- Docker et Docker Compose
- Docker Swarm
- Un compte et un cluster MongoDB Atlas
- Un compte Docker Hub (pour pousser vos images Docker)
- Java 21 et Maven 3.6+ (pour compiler l’application)

## Configuration de MongoDB Atlas

Avant de déployer l'application, vous devez configurer un cluster MongoDB Atlas :

1. **Créer un compte MongoDB Atlas**: Allez sur [MongoDB Atlas](https://www.mongodb.com/atlas) et créez un compte.

2. **Créer un cluster**:

   - Créez un nouveau cluster (le niveau gratuit M0 suffit pour les tests)
   - Choisissez votre fournisseur cloud et région
   - Nommez votre cluster (ex. : "short4us-cluster")

3. **Configurer l’accès à la base de données**:

   - Allez dans "Database Access"
   - Créez un utilisateur avec des droits de lecture/écriture
   - Notez le nom d’utilisateur et le mot de passe

4. **Configurer l’accès réseau**:

   - Allez dans "Network Access"
   - Ajoutez votre adresse IP ou `0.0.0.0/0` pour les tests (non recommandé en production)

5. **Obtenir la chaîne de connexion**:
   - Allez dans "Clusters" puis cliquez sur "Connect"
   - Choisissez "Connect your application"
   - Copiez la chaîne de connexion (ex : `mongodb+srv://username:password@cluster.xxxxx.mongodb.net/`)

## Variables d’environnement

L’application repose sur des variables d’environnement pour la configuration. Créez un fichier `.env` à la racine du projet avec les variables suivantes :

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
> Remplacez `MONGODB_URI` par votre vraie chaîne de connexion MongoDB Atlas, avec votre nom d’utilisateur, mot de passe et détails du cluster.

## Guide d’installation

### 1. Cloner le dépôt

```bash
git clone https://github.com/Isaac-Andradee/short4us.git
cd short4us
```

### 2. Compiler l’application

Compilez tous les microservices avec Maven :

```bash
mvn clean package
```

### 3. Construire et pousser les images Docker

Vous devez construire vos propres images Docker et les pousser sur Docker Hub :

```bash
# Connexion à Docker Hub
docker login

# Construire et taguer les images (remplacez 'yourusername' par votre nom d’utilisateur Docker Hub)
docker build -t yourusername/eureka:latest ./eureka
docker build -t yourusername/shortener-service ./shortener-service
docker build -t yourusername/resolver-service ./resolver-service
docker build -t yourusername/keygen-service ./keygen-service

# Pousser les images
docker push yourusername/eureka:latest
docker push yourusername/shortener-service:latest
docker push yourusername/resolver-service:latest
docker push yourusername/keygen-service:latest
```

### 4. Modifier stack.yml

Après avoir poussé vos images, modifiez `stack.yml` pour utiliser vos images Docker Hub :

```yaml
# Remplacez toutes les occurrences du digest SHA256 par l’image que vous avez construite et poussée sur Docker Hub.
# Exemple:
eureka-server:
  image: yourusername/eureka:latest
  # ... reste de la configuration
shortener-service:
  image: yourusername/shortener-service:latest
  # ... reste de la configuration
resolver-service:
  image: yourusername/resolver-service:latest
  # ... reste de la configuration
keygen-service:
  image: yourusername/keygen-service:latest
  # ... reste de la configuration
```

### 5. Configuration des variables

Créez un fichier `.env` à la racine avec les variables listées plus haut, en configurant correctement MongoDB Atlas.

### 6. Déploiement avec Docker Swarm

```bash
# Initialiser Docker Swarm (si non déjà fait)
docker swarm init

# Déployer la stack
docker stack deploy -c stack.yml short4us

# IMPORTANT : après le déploiement, initialisez le cluster Redis :
docker exec -it $(docker ps | grep redis-node-1 | awk '{print $1}') sh -c "redis-cli --cluster create redis-node-1:6379 redis-node-2:6379 redis-node-3:6379 redis-node-4:6379 redis-node-5:6379 redis-node-6:6379 --cluster-replicas 1"
```

### 7. Vérification

Assurez-vous que tous les services tournent :

```bash
docker service ls
```

Vous devriez voir tous les services (eureka-server, shortener-service, resolver-service, keygen-service et les nœuds Redis) actifs.

## Comment utiliser

Short4Us fournit une API REST pour raccourcir les URLs :

### Raccourcir une URL

Par défaut, l’application tourne sur `http://localhost:80/`, mais vous pouvez tester avec Postman ou dans le terminal :

```bash
curl -X POST http://localhost/shorten \
  -H "Content-Type: application/json" \
  -d '{"longUrl":"https://www.example.com/some/very/long/url/that/you/want/to/shorten", "alias": "optional-custom-alias"}'
```

### Accéder à une URL raccourcie

Accédez simplement à l’URL courte dans votre navigateur :

```
http://localhost/{short-key}
```

Le service vous redirigera automatiquement vers l’URL d’origine.

## Technical Architecture

- **Spring Boot**: Framework de base pour tous les services
- **Spring Cloud**: Pour la configuration distribuée et la découverte de services
- **MongoDB Atlas**: Base de données cloud pour le stockage persistant
- **Redis Cluster**: Cache distribué pour de hautes performances
- **Nginx**: Passerelle API pour le routage des requêtes
- **Docker & Docker Swarm**: Pour la conteneurisation et l’orchestration

## Contribuer

Les contributions sont les bienvenues ! Pour contribuer :

1. Forkez le dépôt
2. Créez une branche (`git checkout -b feature/amazing-feature`)
3. Commitez vos changements (`git commit -m 'Add an amazing feature'`)
4. Poussez la branche (`git push origin feature/amazing-feature`)
5. Ouvrez une Pull Request

## Contact

Isaac Andrade - [@Isaac-Andradee](isaac.andra84@gmail.com)
