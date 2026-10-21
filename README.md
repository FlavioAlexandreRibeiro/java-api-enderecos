# API de Endereços

Uma API RESTful desenvolvida com Spring Boot para gerenciamento de clientes e endereços. Esta aplicação fornece endpoints completos para operações CRUD (Create, Read, Update, Delete) em um banco de dados PostgreSQL.

## 🚀 Funcionalidades

- **Gerenciamento de Clientes**: Operações completas de CRUD para cadastro de clientes
- **Gerenciamento de Endereços**: Operações completas de CRUD para cadastro de endereços
- **Validação de Dados**: Validação automática de campos obrigatórios, formato de e-mail, telefone e CEP
- **Tratamento de Exceções**: Tratamento global de erros com mensagens claras
- **E-mail Único**: Garantia de unicidade de e-mail no cadastro de clientes
- **Relacionamento**: Cada endereço deve estar associado a um cliente existente
- **Filtros Dinâmicos**: Listagem de endereços com filtros opcionais por rua, número, bairro, cidade, estado, CEP e cliente
- **DTOs**: Separação clara entre entidades de banco e objetos de transferência de dados
- **Testes Unitários**: Cobertura de testes para a camada de serviço com JUnit e Mockito
- **Paginação**: Listagem de endereços com suporte a paginação

## 🛠️ Tecnologias Utilizadas

- Java 17
- Spring Boot 4.0.7
- Spring Data JPA
- Spring Web MVC
- Spring Validation
- PostgreSQL
- Lombok
- Maven
- JUnit 5
- Mockito

## 📋 Pré-requisitos

- Java 17 ou superior instalado
- Maven 3.6+ instalado
- PostgreSQL 12+ instalado e rodando
- IDE de sua preferência (IntelliJ IDEA, Eclipse, VS Code, etc.)

## 🔧 Instalação e Configuração

### 1. Clone o repositório

```bash
git clone <repository-url>
cd API-de-Enderecos
```

### 2. Configure o Banco de Dados

Crie um banco de dados PostgreSQL:

```sql
CREATE DATABASE api_enderecos;
```

### 3. Configure as Credenciais

Edite o arquivo `src/main/resources/application.properties` ou configure as variáveis de ambiente:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/api_enderecos
spring.datasource.username=postgres
spring.datasource.password=sua_senha
```

Ou usando variáveis de ambiente:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/api_enderecos
export DB_USERNAME=postgres
export DB_PASSWORD=sua_senha
```

### 4. Compile o Projeto

```bash
./mvnw clean install
```

Ou usando Maven diretamente:

```bash
mvn clean install
```

## ▶️ Executando a Aplicação

### Via Maven

```bash
./mvnw spring-boot:run
```

### Via JAR

```bash
java -jar target/API-de-Enderecos-0.0.1-SNAPSHOT.jar
```

A aplicação estará disponível em: http://localhost:8080

## 📡 Endpoints da API

### Clientes

#### Criar Cliente

```http
POST /clientes
Content-Type: application/json

{
  "nome": "João Silva",
  "email": "joao.silva@email.com",
  "telefone": "11999999999"
}
```

**Response: 201 Created**

```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao.silva@email.com",
  "telefone": "11999999999"
}
```

#### Listar Todos os Clientes

```http
GET /clientes
```

**Response: 200 OK**

```json
[
  {
    "id": 1,
    "nome": "João Silva",
    "email": "joao.silva@email.com",
    "telefone": "11999999999"
  }
]
```

#### Buscar Cliente por ID

```http
GET /clientes/{id}
```

**Response: 200 OK**

```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao.silva@email.com",
  "telefone": "11999999999"
}
```

#### Atualizar Cliente

```http
PUT /clientes/{id}
Content-Type: application/json

{
  "nome": "João Silva Atualizado",
  "email": "joao.atualizado@email.com",
  "telefone": "11988888888"
}
```

**Response: 200 OK**

```json
{
  "id": 1,
  "nome": "João Silva Atualizado",
  "email": "joao.atualizado@email.com",
  "telefone": "11988888888"
}
```

#### Deletar Cliente

```http
DELETE /clientes/{id}
```

**Response: 204 No Content**

### Endereços

#### Criar Endereço

```http
POST /enderecos
Content-Type: application/json

{
  "rua": "Rua das Flores",
  "numero": 123,
  "complemento": "Apto 101",
  "bairro": "Centro",
  "cidade": "São Paulo",
  "estado": "SP",
  "cep": "01234-567",
  "clienteId": 1
}
```

**Response: 201 Created**

```json
{
  "id": 1,
  "rua": "Rua das Flores",
  "numero": 123,
  "complemento": "Apto 101",
  "bairro": "Centro",
  "cidade": "São Paulo",
  "estado": "SP",
  "cep": "01234-567",
  "clienteId": 1
}
```

#### Listar Endereços (com filtros e paginação)

```http
GET /enderecos?page=0&size=10&rua=&numero=&bairro=&cidade=&estado=&cep=&clienteId=
```

**Response: 200 OK**

```json
{
  "content": [
    {
      "id": 1,
      "rua": "Rua das Flores",
      "numero": 123,
      "complemento": "Apto 101",
      "bairro": "Centro",
      "cidade": "São Paulo",
      "estado": "SP",
      "cep": "01234-567",
      "clienteId": 1
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 1,
  "totalPages": 1
}
```

#### Buscar Endereço por ID

```http
GET /enderecos/{id}
```

**Response: 200 OK**

```json
{
  "id": 1,
  "rua": "Rua das Flores",
  "numero": 123,
  "complemento": "Apto 101",
  "bairro": "Centro",
  "cidade": "São Paulo",
  "estado": "SP",
  "cep": "01234-567",
  "clienteId": 1
}
```

#### Atualizar Endereço

```http
PUT /enderecos/{id}
Content-Type: application/json

{
  "rua": "Rua das Flores Atualizada",
  "numero": 456,
  "complemento": "Apto 202",
  "bairro": "Centro",
  "cidade": "São Paulo",
  "estado": "SP",
  "cep": "01234-567",
  "clienteId": 1
}
```

**Response: 200 OK**

```json
{
  "id": 1,
  "rua": "Rua das Flores Atualizada",
  "numero": 456,
  "complemento": "Apto 202",
  "bairro": "Centro",
  "cidade": "São Paulo",
  "estado": "SP",
  "cep": "01234-567",
  "clienteId": 1
}
```

#### Deletar Endereço

```http
DELETE /enderecos/{id}
```

**Response: 204 No Content**

## 🧪 Testes

Para executar os testes unitários:

```bash
./mvnw test
```

Ou usando Maven:

```bash
mvn test
```

## 📄 Estrutura do Projeto

```
API-de-Enderecos/
├── src/
│   ├── main/
│   │   ├── java/com/API_de_Enderecos/
│   │   │   ├── controller/     # Controladores REST
│   │   │   ├── dto/            # Data Transfer Objects
│   │   │   ├── entity/         # Entidades JPA
│   │   │   ├── exception/      # Tratamento de exceções
│   │   │   ├── repository/     # Repositórios JPA
│   │   │   ├── service/        # Lógica de negócio
│   │   │   └── specification/  # Especificações JPA
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/API_de_Enderecos/
│           └── service/        # Testes unitários
├── postman_collection.json
├── pom.xml
└── README.md
```

## 🔍 Validações

### Cliente

- **nome**: Obrigatório, entre 3 e 100 caracteres
- **email**: Obrigatório, formato válido de e-mail, máximo 100 caracteres
- **email**: Deve ser único
- **telefone**: Opcional, máximo 20 caracteres

### Endereço

- **rua**: Obrigatório, máximo 100 caracteres
- **numero**: Obrigatório
- **complemento**: Opcional, máximo 100 caracteres
- **bairro**: Obrigatório, máximo 50 caracteres
- **cidade**: Obrigatório, máximo 100 caracteres
- **estado**: Obrigatório, exatamente 2 caracteres (UF)
- **cep**: Obrigatório, formato 00000-000 ou 00000000
- **clienteId**: Obrigatório, deve referenciar um cliente existente

## ⚠️ Tratamento de Erros

A API retorna códigos de status HTTP apropriados:

- **201 Created**: Recurso criado com sucesso
- **200 OK**: Operação realizada com sucesso
- **204 No Content**: Recurso deletado com sucesso
- **400 Bad Request**: Dados inválidos
- **404 Not Found**: Recurso não encontrado
- **409 Conflict**: E-mail já cadastrado
- **500 Internal Server Error**: Erro interno no servidor

## 📝 Coleção Postman

Uma coleção Postman está disponível no arquivo `postman_collection.json` para facilitar os testes dos endpoints.

Para importar:

1. Abra o Postman
2. Clique em "Import"
3. Selecione o arquivo `postman_collection.json`
4. A coleção será carregada com todos os endpoints organizados

## 🤝 Contribuindo

Contribuições são bem-vindas! Por favor:

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/MinhaFeature`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/MinhaFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.

## 👤 Autor

Flavio Alexandre Ribeiro

Desenvolvido como parte do projeto de API de Endereços.

## 📧 Contato

GitHub: https://github.com/FlavioAlexandreRibeiro
LinkedIn: https://www.linkedin.com/in/flavioalexandreribeiro1986/?isSelfProfile=true

Para dúvidas ou sugestões, abra uma issue no repositório.
