# Budgeting AI — Assistente Financeiro Inteligente com Reconhecimento de Fala

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-green?style=for-the-badge&logo=springboot)
![Spring AI](https://img.shields.io/badge/Spring%20AI-1.0.0-blue?style=for-the-badge)
![Gemini](https://img.shields.io/badge/Gemini-2.0%20Flash-purple?style=for-the-badge&logo=google)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)

> Projeto final do **Bootcamp NTT Data Engenharia com Java e IA**, desenvolvido como parte da trilha de backend com integração de Inteligência Artificial.

---

##Sobre o Projeto

O **Budgeting AI** é uma API REST inteligente que combina gerenciamento financeiro com Inteligência Artificial generativa. Através do assistente virtual **BudgetBot**, o usuário pode:

- Registrar receitas e despesas
- Consultar seu saldo e resumo financeiro
- Fazer perguntas em linguagem natural sobre suas finanças
- Enviar mensagens de voz que são transcritas e respondidas pela IA

---

##Arquitetura

```
src/main/java/dio/budgeting/
├── config/
│   └── ChatClientConfig.java       # Configuração do cliente Spring AI
├── controller/
│   ├── TransactionController.java  # Endpoints REST de transações
│   ├── ChatController.java         # Endpoint do assistente IA
│   └── TranscriptionController.java # Endpoint de transcrição de áudio
├── model/
│   └── Transaction.java            # Entidade de transação financeira
├── repository/
│   └── TransactionRepository.java  # Repositório JPA
├── service/
│   └── AssistantService.java       # Lógica do assistente com contexto financeiro
└── BudgetingApplication.java
```

---

##Tecnologias Utilizadas

| Tecnologia | Versão | Finalidade |
|---|---|---|
| Java | 21 | Linguagem principal |
| Spring Boot | 3.4.5 | Framework backend |
| Spring AI | 1.0.0-M6 | Integração com LLMs |
| Gemini 2.0 Flash | — | Modelo de linguagem (chat) |
| Whisper | v1 | Transcrição de áudio |
| Spring Data JPA | — | Persistência de dados |
| H2 Database | — | Banco de dados em memória |
| Lombok | — | Redução de boilerplate |
| Docker | — | Infraestrutura |

---

## Endpoints da API

### Transações

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/transactions` | Lista todas as transações |
| `GET` | `/api/transactions/{id}` | Busca transação por ID |
| `POST` | `/api/transactions` | Cria nova transação |
| `DELETE` | `/api/transactions/{id}` | Remove transação |
| `GET` | `/api/transactions/summary` | Resumo financeiro (receitas, despesas, saldo) |

### Assistente IA

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/assistant/chat` | Envia mensagem de texto para o BudgetBot |
| `POST` | `/api/assistant/transcribe` | Envia áudio, transcreve e obtém resposta da IA |

---

## Como Executar

### Pré-requisitos

- Java 21+
- Docker Desktop
- Chave de API do [Google AI Studio](https://aistudio.google.com)

### 1. Clone o repositório

```bash
git clone https://github.com/gabrielcanjos/budgeting.git
cd budgeting
```

### 2. Configure as variáveis

Edite `src/main/resources/application.properties`:

```properties
spring.ai.openai.api-key=SUA_CHAVE_GEMINI_AQUI
```

### 3. Suba o banco de dados

```bash
docker-compose up -d
```

### 4. Execute a aplicação

```bash
./gradlew bootRun
```

A aplicação estará disponível em `http://localhost:8081`

---

## Exemplos de Uso

### Criar uma transação

```bash
curl -X POST http://localhost:8081/api/transactions \
  -H "Content-Type: application/json" \
  -d '{"description":"Salário","amount":5000,"type":"INCOME","category":"Trabalho"}'
```

### Consultar resumo financeiro

```bash
curl http://localhost:8081/api/transactions/summary
```

### Perguntar ao assistente IA

```bash
curl -X POST http://localhost:8081/api/assistant/chat \
  -H "Content-Type: application/json" \
  -d '{"message":"Como está minha situação financeira?"}'
```

**Resposta do BudgetBot:**
```json
{
  "response": "Olá! Com base nos seus dados, você possui uma receita total de R$ 5.000,00 
  e despesas de R$ 1.500,00, resultando em um saldo positivo de R$ 3.500,00. 
  Sua saúde financeira está boa! Deseja alguma dica para otimizar ainda mais seu orçamento?"
}
```

---

## Funcionalidades Implementadas

- [x] CRUD de transações financeiras
- [x] Resumo financeiro automático (receitas, despesas, saldo)
- [x] Assistente IA com contexto financeiro personalizado
- [x] Transcrição de áudio via Whisper (Speech-to-Text)
- [x] Integração com Gemini via Spring AI
- [x] API REST documentada
- [x] Infraestrutura com Docker

---

## Aprendizados

Este projeto foi desenvolvido como parte do **Bootcamp NTT Data — Engenharia com Java e IA** na plataforma [DIO](https://www.dio.me), cobrindo:

- Desenvolvimento de APIs REST com Spring Boot
- Integração de IA generativa com Spring AI
- Transcrição de áudio com Whisper OpenAI
- Persistência de dados com Spring Data JPA
- Containerização com Docker
- Clean Architecture no backend Java

---

## Autor

Desenvolvido com Thiago Poiani durante o Bootcamp NTT Data na DIO.
