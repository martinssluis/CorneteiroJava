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

### 2. Executar com Docker

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
