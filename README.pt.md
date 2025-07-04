# Short4Us: Um Encurtador de URL com Spring Boot

[![GitHub Stars](https://img.shields.io/github/stars/Isaac-Andradee/short4us.svg)](https://github.com/Isaac-Andradee/short4us/stargazers)
[![GitHub Issues](https://img.shields.io/github/issues/Isaac-Andradee/short4us.svg)](https://github.com/Isaac-Andradee/short4us/issues)
[![Current Version](https://img.shields.io/github/v/release/Isaac-Andradee/short4us)](https://github.com/Isaac-Andradee/short4us/releases)
[![Build Status](https://img.shields.io/github/actions/workflow/status/Isaac-Andradee/short4us/.github%2Fworkflows%2Fmaven-build-main.yml)](https://github.com/Isaac-Andradee/short4us/actions/workflows/maven-build-main.yml)
[![Contributions](https://img.shields.io/github/contributors/Isaac-Andradee/short4us.svg)](https://github.com/Isaac-Andradee/short4us/graphs/contributors)
[![Top Language](https://img.shields.io/github/languages/top/Isaac-Andradee/short4us)](https://github.com/search?q=repo%3AIsaac-Andradee%2Fshort4us++language%3AJava&type=code)

Short4Us é um sistema de encurtamento de URLs desenvolvido com Spring Boot e arquitetura de microsserviços. O sistema é totalmente escalável usando Docker Swarm, permitindo encurtar URLs longas de forma eficiente e confiável.

![Screenshot](https://github.com/user-attachments/assets/d419bea8-d448-4e3a-b950-19a833c06f82)

## Arquitetura

Short4Us consiste em vários microsserviços trabalhando em conjunto:

1. **Serviço Encurtador**: Responsável por encurtar URLs longas, gerando chaves curtas.
2. **Serviço de Resolução**: Redireciona os usuários das URLs curtas para as URLs originais.
3. **Serviço de Geração de Chaves**: Gera chaves únicas para URLs encurtadas.
4. **Servidor Eureka**: Serviço de descoberta para registrar e localizar microsserviços.
5. **Gateway de API**: Gateway para roteamento de requisições para os serviços apropriados.

A aplicação usa Redis para cache de alto desempenho e MongoDB Atlas para armazenamento persistente, projetada para escalar horizontalmente.

> [!TIP]
> Você pode ver o fluxo de design do sistema e dos serviços [aqui](https://github.com/Isaac-Andradee/short4us/blob/main/SYSTEM-DESIGN.md)

## Pré-requisitos

Para rodar o Short4Us, você precisará de:

- Docker e Docker Compose
- Docker Swarm
- Conta no MongoDB Atlas e um cluster
- Conta no Docker Hub (para enviar imagens personalizadas)
- Java 21 e Maven 3.6+ (para compilar a aplicação)

## Configuração do MongoDB Atlas

Antes de implantar a aplicação, você precisa configurar um cluster no MongoDB Atlas:

1. **Crie uma Conta no MongoDB Atlas**: Acesse [MongoDB Atlas](https://www.mongodb.com/atlas) e crie uma conta.

2. **Crie um Cluster**:

   - Crie um novo cluster (o plano gratuito M0 é suficiente para testes)
   - Escolha seu provedor de nuvem e região preferida
   - Dê um nome ao seu cluster (ex: "short4us-cluster")

3. **Configure o Acesso ao Banco de Dados**:

   - Vá até "Database Access" no painel do Atlas
   - Crie um usuário com permissões de leitura/escrita
   - Anote o nome de usuário e a senha

4. **Configure o Acesso à Rede**:

   - Vá até "Network Access" no painel do Atlas
   - Adicione seu endereço IP ou `0.0.0.0/0` para testes (não recomendado em produção)

5. **Obtenha a String de Conexão**:
   - Vá em "Clusters" e clique em "Connect"
   - Escolha "Connect your application"
   - Copie a string de conexão (algo como: `mongodb+srv://username:password@cluster.xxxxx.mongodb.net/`)

## Variáveis de Ambiente

A aplicação depende de variáveis de ambiente para configuração. Crie um arquivo `.env` na raiz do projeto com as seguintes variáveis:

```
# Configuração do Eureka
EUREKA_SERVER_HOST=eureka-server
EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-server:8761/eureka/
EUREKA_SERVER_PORT=8761

# Configuração do MongoDB
MONGODB_URI=mongodb+srv://username:password@cluster.xxxxx.mongodb.net/short4us?retryWrites=true&w=majority
MONGODB_DATABASE=short4us
MONGO_USERNAME=username
MONGO_PASSWORD=password

# Configuração do Redis
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

# Portas dos Serviços
SHORTENER_SERVICE_PORT=8080
RESOLVER_SERVICE_PORT=8083
KEYGEN_SERVICE_PORT=8081

# Configuração de Domínio
SHORT_DOMAIN=http://localhost/
```

> [!IMPORTANT]
> Substitua o `MONGODB_URI` com sua string de conexão real do MongoDB Atlas, incluindo seu nome de usuário, senha e detalhes do cluster.

## Guia de Instalação

### 1. Clone o Repositório

```bash
git clone https://github.com/Isaac-Andradee/short4us.git
cd short4us
```

### 2. Compile a Aplicação

Compile todos os microsserviços usando Maven:

```bash
mvn clean package
```

### 3. Construa e Envie as Imagens Docker

Você precisará construir suas próprias imagens Docker e enviá-las para o Docker Hub:

```bash
# Faça login no Docker Hub
docker login

# Construa e marque as imagens (substitua 'yourusername' pelo seu usuário do Docker Hub)
docker build -t yourusername/eureka:latest ./eureka
docker build -t yourusername/shortener-service ./shortener-service
docker build -t yourusername/resolver-service ./resolver-service
docker build -t yourusername/keygen-service ./keygen-service

# Envie as imagens para o Docker Hub
docker push yourusername/eureka:latest
docker push yourusername/shortener-service:latest
docker push yourusername/resolver-service:latest
docker push yourusername/keygen-service:latest
```

### 4. Atualize o stack.yml

Depois de enviar suas imagens, atualize o arquivo stack.yml para usar suas imagens do Docker Hub:

```yaml
# Substitua todas as ocorrências de digest sha256 pela imagem do Docker Hub que você criou
# Exemplo:
eureka-server:
  image: yourusername/eureka:latest
  # ... restante da configuração

shortener-service:
  image: yourusername/shortener-service:latest
  # ... restante da configuração

resolver-service:
  image: yourusername/resolver-service:latest
  # ... restante da configuração

keygen-service:
  image: yourusername/keygen-service:latest
  # ... restante da configuração
```

### 5. Configuração de Ambiente

Crie um arquivo `.env` na raiz do projeto com as variáveis de ambiente listadas acima, garantindo que configure corretamente a string de conexão do MongoDB Atlas.

### 6. Implantação com Docker Swarm

```bash
# Inicialize o Docker Swarm (se ainda não estiver configurado)
docker swarm init

# Implante a stack
docker stack deploy -c stack.yml short4us

# IMPORTANTE: Após a implantação, inicialize o cluster Redis conectando-se a um dos nós Redis e executando:
docker exec -it $(docker ps | grep redis-node-1 | awk '{print $1}') sh -c "redis-cli --cluster create redis-node-1:6379 redis-node-2:6379 redis-node-3:6379 redis-node-4:6379 redis-node-5:6379 redis-node-6:6379 --cluster-replicas 1"
```

### 7. Verifique a Implantação

Verifique se todos os serviços estão em execução:

```bash
docker service ls
```

Você deve ver todos os serviços (eureka-server, shortener-service, resolver-service, keygen-service e nós Redis) em execução.

## Como utilizar

Short4Us fornece uma API REST para encurtar URLs:

### Encurtar uma URL

A aplicação roda por padrão em `http://localhost:80/`, mas você pode testá-la no Postman ou no terminal:

```bash
curl -X POST http://localhost/shorten \
  -H "Content-Type: application/json" \
  -d '{"longUrl":"https://www.example.com/some/very/long/url/that/you/want/to/shorten", "alias": "optional-custom-alias"}'
```

### Acessar a URL Encurtada

Basta acessar a URL curta no navegador:

```
http://localhost/{short-key}
```

O serviço irá redirecionar automaticamente para a URL original.

## Arquitetura Técnica

- **Spring Boot**: Framework base para todos os serviços
- **Spring Cloud**: Para configuração distribuída e descoberta de serviços
- **MongoDB Atlas**: Banco de dados hospedado na nuvem para armazenamento persistente
- **Redis Cluster**: Cache distribuído para alto desempenho
- **Nginx**: Como API Gateway para roteamento de requisições
- **Docker & Docker Swarm**: Para conteinerização e orquestração

## Contribuindo

Contribuições são bem-vindas! Para contribuir:

1. Faça um fork do repositório
2. Crie sua branch de funcionalidade (`git checkout -b feature/amazing-feature`)
3. Faça commit das suas mudanças (`git commit -m 'Add an amazing feature'`)
4. Envie a branch para o repositório remoto (`git push origin feature/amazing-feature`)
5. Abra um Pull Request

## Contato

Isaac Andrade - [@Isaac-Andradee](mailto:isaac.andra84@gmail.com)
