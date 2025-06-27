# Encurtador de URL

Um aplicativo front-end de encurtamento de URLs que se comunica com uma API Nginx para gerar links curtos.

## Estrutura do Projeto

O projeto foi organizado seguindo os princípios SOLID, com cada componente tendo uma única responsabilidade:

- **config.js**: Contém as configurações da aplicação, como URLs da API e endpoints
- **ui-elements.js**: Gerencia os elementos da interface do usuário (DOM)
- **ui-service.js**: Serviço para manipulação da interface do usuário
- **api-service.js**: Serviço para comunicação com a API
- **url-validator.js**: Serviço para validação de URLs
- **app.js**: Lógica principal da aplicação
- **index.js**: Arquivo principal responsável por inicializar a aplicação

## Como Usar

1. Abra o arquivo `index.html` no navegador
2. Insira a URL longa que deseja encurtar
3. Opcionalmente, forneça um alias personalizado
4. Clique em "Encurtar" para gerar a URL curta

## Requisitos

- A API Nginx deve estar em execução no endereço configurado em `config.js`
- Navegador web moderno com suporte a JavaScript ES6 