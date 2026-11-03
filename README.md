# ⚽ Corneteiro (Java Version)

Projeto backend desenvolvido em **Java com Spring Boot** com o objetivo de consumir dados da API do Cartola FC e fornecer informações organizadas sobre jogadores.

Este projeto é uma evolução da versão inicial em **Flask**, agora com uma arquitetura mais organizada e escalável.

---

## 🚀 Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Web
- RestClient
- Jackson
- Maven

---

## 🧠 Objetivo do projeto

O **Corneteiro** tem como objetivo:

- Buscar atletas por nome
- Organizar dados da API do Cartola
- Servir como base para futuras análises e recomendações

---

## 🏗️ Arquitetura

O projeto segue uma separação clara de responsabilidades:

- **controller** → recebe requisições HTTP  
- **service** → contém a regra de negócio  
- **client** → integra com a API externa  
- **dto** → estrutura os dados  
- **config** → configurações da aplicação  

---

## 📂 Estrutura do projeto

```text
src/main/java/corneteiro/
├── client/
├── config/
├── controller/
├── dto/
├── service/
````
---

## ⚙️ Como executar o projeto

### 1. Clonar o repositório

```bash
git clone https://github.com/martinssluis/CorneteiroJava.git
cd CorneteiroJava
```

### 2. Executar localmente (sem Docker)

**Pré-requisitos:** JDK 21+ e Maven 3.9+ (ou use o wrapper `./mvnw`, que dispensa instalar o Maven).

```bash
mvn spring-boot:run
```

A API sobe em `http://localhost:1895` e o Swagger em `http://localhost:1895/swagger-ui.html`.
Para parar, use `Ctrl+C` no terminal.

Outros comandos úteis:

```bash
mvn clean compile   # compila e verifica erros
mvn test            # roda os testes
mvn clean package   # gera o JAR em target/
```

> O frontend Angular (`http://localhost:4200`) tem CORS liberado nesta API.
> Para rodar o frontend, veja o README em `CorneteiroAngular/corneteiro`.

### 3. Executar com Docker

#### Build e iniciar a aplicação

```bash
docker compose up --build
```

Esse comando vai:
- Compilar o projeto com Maven dentro de um container
- Criar a imagem Docker da aplicação
- Iniciar o container em background

#### Parar a aplicação

```bash
docker compose down
```

#### Ver logs em tempo real

```bash
docker compose logs -f app
```

#### Reconstruir (sem cache)

```bash
docker compose build --no-cache
docker compose up
```

## 🌐 Acessando a aplicação

Após iniciar, a aplicação estará disponível em:

```
http://localhost:1895
```

### Exemplos de requisições

**Buscar todos os atletas:**
```bash
curl http://localhost:1895/atletas
```

**Buscar atleta por ID:**
```bash
curl http://localhost:1895/atletas/1
```

**Buscar todos os clubes:**
```bash
curl http://localhost:1895/clubes
```

## 📖 Documentação da API (Swagger)

Com a aplicação rodando:

| Recurso | URL |
|---------|-----|
| Swagger UI (interativo) | http://localhost:1895/swagger-ui.html |
| OpenAPI (JSON) | http://localhost:1895/v3/api-docs |

### Endpoints

| Método | Rota | Descrição | Respostas |
|--------|------|-----------|-----------|
| GET | `/atletas` | Lista todos os atletas do mercado | 200, 500 |
| GET | `/atletas/buscar?nome=&exato=` | Busca atletas por nome (parcial por padrão) | 200, 400, 500 |
| GET | `/atletas/{id}` | Busca atleta por ID | 200, 404, 500 |
| GET | `/clubes` | Lista todos os clubes | 200, 500 |
| GET | `/clubes/buscar?nome=&exato=` | Busca clubes por nome (parcial por padrão) | 200, 400, 500 |
| GET | `/clubes/{id}` | Busca clube por ID | 200, 404, 500 |
| GET | `/recomendacoes/custo-beneficio` | Ranking de atletas por média ÷ preço | 200, 400, 404, 500 |
| GET | `/recomendacoes/custo-beneficio/clubes` | Ranking de clubes pela média do custo-benefício | 200, 400, 500 |

Parâmetros das recomendações: `posicaoId`, `clubeId` (só em atletas), `minJogos` (padrão 3),
`status` (padrão 7, aceita vários separados por vírgula) e `limite` (padrão 20, máximo 100).
Cada item traz o campo `motivo` com os valores usados no cálculo.

Erros seguem o formato `ApiErrorResponse` (`timestamp`, `status`, `error`, `message`, `path`).

> Os campos de atleta com mais de uma palavra são serializados em `snake_case`
> (`atleta_id`, `preco_num`, `pontos_num`). O trecho `FORMATO` da URL de `foto` deve ser
> trocado pelo tamanho desejado, como `80x80`.
>
> Nos clubes, a Cartola envia só a sigla (`nome`, ex.: `FLA`) e o apelido (`apelido`, ex.: `Mengão`).
> O nome oficial (`nome_completo`, ex.: `Flamengo`) é derivado do `slug` pelo backend.

## 🛠️ Solução de problemas

| Sintoma | Causa provável |
|---------|----------------|
| `GET /atletas` ou `/clubes` retorna 500 | A API do Cartola está inacessível. Redes com filtro de conteúdo (ex.: FortiGuard) podem bloquear `api.cartola.globo.com`. Teste com `curl https://api.cartola.globo.com/atletas/mercado` |
| `Port 1895 already in use` | Outro processo usa a porta. No Windows: `netstat -ano \| findstr :1895` |
